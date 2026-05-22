/**
 * auth.js - Gerenciamento de autenticação
 */
const Auth = {
    TOKEN_KEY: 'consultorio_token',
    USER_KEY: 'consultorio_user',

    getToken() { return sessionStorage.getItem(this.TOKEN_KEY); },
    getUser() {
        const u = sessionStorage.getItem(this.USER_KEY);
        return u ? JSON.parse(u) : null;
    },
    isLoggedIn() { return !!this.getToken(); },
    getPerfil() { const u = this.getUser(); return u ? u.perfil : null; },
    isAdmin() { return this.getPerfil() === 'ADMIN'; },
    isMedico() { return this.getPerfil() === 'MEDICO'; },
    isRecepcionista() { return this.getPerfil() === 'RECEPCIONISTA'; },

    async login(email, senha) {
        const res = await fetch('/api/auth/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, senha })
        });
        if (!res.ok) {
            const err = await res.json();
            throw new Error(err.mensagem || 'Credenciais inválidas');
        }
        const data = await res.json();
        sessionStorage.setItem(this.TOKEN_KEY, data.token);
        sessionStorage.setItem(this.USER_KEY, JSON.stringify(data));
        return data;
    },

    logout() {
        sessionStorage.removeItem(this.TOKEN_KEY);
        sessionStorage.removeItem(this.USER_KEY);
        window.location.href = '/index.html';
    },

    requireAuth() {
        if (!this.isLoggedIn()) {
            window.location.href = '/index.html';
            return false;
        }
        return true;
    }
};
