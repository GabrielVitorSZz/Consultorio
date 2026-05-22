/**
 * api.js - Módulo de comunicação com a API REST
 */
const API_BASE = '/api';

const Api = {
    _getHeaders() {
        const token = Auth.getToken();
        const h = { 'Content-Type': 'application/json' };
        if (token) h['Authorization'] = 'Bearer ' + token;
        return h;
    },

    async _request(method, path, body) {
        try {
            const opts = { method, headers: this._getHeaders() };
            if (body) opts.body = JSON.stringify(body);
            const res = await fetch(API_BASE + path, opts);
            if (res.status === 401) { Auth.logout(); return; }
            const text = await res.text();
            const data = text ? JSON.parse(text) : null;
            if (!res.ok) throw new Error(data?.mensagem || 'Erro na requisição');
            return data;
        } catch (e) {
            if (e.message !== 'Erro na requisição') throw e;
            throw e;
        }
    },

    get(path) { return this._request('GET', path); },
    post(path, body) { return this._request('POST', path, body); },
    put(path, body) { return this._request('PUT', path, body); },
    delete(path) { return this._request('DELETE', path); },
};
