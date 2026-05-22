/**
 * utils.js - Utilitários gerais
 */

// Toast notifications
function showToast(msg, type = 'info') {
    let container = document.getElementById('toast-container');
    if (!container) {
        container = document.createElement('div');
        container.id = 'toast-container';
        document.body.appendChild(container);
    }
    const toast = document.createElement('div');
    const icons = { success: '✅', error: '❌', warning: '⚠️', info: 'ℹ️' };
    toast.className = `toast ${type}`;
    toast.innerHTML = `<span>${icons[type] || 'ℹ️'}</span><span>${msg}</span>`;
    container.appendChild(toast);
    setTimeout(() => { toast.style.opacity = '0'; toast.style.transition = 'opacity 0.3s'; setTimeout(() => toast.remove(), 300); }, 3500);
}

// Modal
function openModal(id) { document.getElementById(id).style.display = 'flex'; }
function closeModal(id) { document.getElementById(id).style.display = 'none'; }

// Format date
function fmtDate(str) {
    if (!str) return '-';
    if (str.includes('/')) return str;
    const d = new Date(str);
    if (isNaN(d)) return str;
    return d.toLocaleDateString('pt-BR');
}

function fmtDateTime(str) {
    if (!str) return '-';
    if (str.includes('/')) return str;
    const d = new Date(str);
    if (isNaN(d)) return str;
    return d.toLocaleDateString('pt-BR') + ' ' + d.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' });
}

function badgeStatus(status) {
    const map = {
        AGENDADA: ['badge-info', 'Agendada'],
        REALIZADA: ['badge-success', 'Realizada'],
        CANCELADA: ['badge-danger', 'Cancelada'],
        FALTA: ['badge-warning', 'Falta']
    };
    const [cls, label] = map[status] || ['badge-secondary', status];
    return `<span class="badge ${cls}">${label}</span>`;
}

// Confirm dialog
function confirmacao(msg) { return confirm(msg); }

// Render navbar user
function renderNavUser() {
    const user = Auth.getUser();
    if (!user) return;
    const el = document.getElementById('nav-user-info');
    if (el) el.innerHTML = `<strong>${user.nome}</strong><span>${user.perfil}</span>`;
    // Show admin menus
    if (Auth.isAdmin()) {
        document.querySelectorAll('.admin-only').forEach(el => el.style.display = 'block');
    }
    // Hide medico-only for non-medicos
    if (!Auth.isMedico()) {
        document.querySelectorAll('.medico-only').forEach(el => el.style.display = 'none');
    }
}

// Loading helpers
function showLoading(containerId) {
    document.getElementById(containerId).innerHTML = '<div class="loading"><div class="spinner"></div>Carregando...</div>';
}

function escapeHtml(str) {
    if (!str) return '';
    return str.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;');
}
