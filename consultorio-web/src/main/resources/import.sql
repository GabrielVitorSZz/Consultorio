-- =============================================
-- Script de carga inicial de dados - Consultório
-- =============================================

-- Perfis de Usuário
INSERT INTO perfil_usuario (id, nome, descricao) VALUES (1, 'ADMIN', 'Administrador do sistema com acesso total');
INSERT INTO perfil_usuario (id, nome, descricao) VALUES (2, 'MEDICO', 'Médico - acesso a consultas e histórico');
INSERT INTO perfil_usuario (id, nome, descricao) VALUES (3, 'RECEPCIONISTA', 'Recepcionista - agendamentos e pacientes');

-- Usuários (todos com senha "123456", hash BCrypt $2a$10$)
INSERT INTO usuario (id, nome, email, senha, ativo, perfil_id) VALUES
  (1, 'Administrador', 'admin@consultorio.com', '$2a$10$U57/xxOS/zC69IVGdF.9Qe3uXl4isYZWFnfHE8yPoctYN1vVcW6g2', true, 1);
INSERT INTO usuario (id, nome, email, senha, ativo, perfil_id) VALUES
  (2, 'Dr. Carlos Silva', 'carlos@consultorio.com', '$2a$10$9UN61/dlU22KXt08IWjVmeLw41YRWPycXRfqqr8UBzvGMoeFGSoNO', true, 2);
INSERT INTO usuario (id, nome, email, senha, ativo, perfil_id) VALUES
  (3, 'Dra. Ana Lima', 'ana@consultorio.com', '$2a$10$/iDEeloHG11mxjPCKp4uxOi6n4eNfs5D8emRTlndRn4VRg5GyQPyu', true, 2);
INSERT INTO usuario (id, nome, email, senha, ativo, perfil_id) VALUES
  (4, 'Maria Recepcionista', 'maria@consultorio.com', '$2a$10$KuEBjOhG4ZIqM1VQhPbE3eiLyXk1F6fnWBiJVsn/jHp07eb3LSVB6', true, 3);

-- Médicos
INSERT INTO medico (id, crm, especialidade, telefone, usuario_id) VALUES
  (1, 'CRM-SP 12345', 'Clínica Geral', '(11) 9999-1111', 2);
INSERT INTO medico (id, crm, especialidade, telefone, usuario_id) VALUES
  (2, 'CRM-SP 67890', 'Cardiologia', '(11) 9999-2222', 3);

-- Pacientes
INSERT INTO paciente (id, nome, cpf, telefone, email, data_nascimento, sexo, observacoes, ativo) VALUES
  (1, 'João Pereira', '111.222.333-44', '(11) 8888-1111', 'joao@email.com', '1985-03-15', 'M', 'Hipertenso', true);
INSERT INTO paciente (id, nome, cpf, telefone, email, data_nascimento, sexo, observacoes, ativo) VALUES
  (2, 'Fernanda Costa', '222.333.444-55', '(11) 8888-2222', 'fernanda@email.com', '1992-07-22', 'F', 'Alérgica a dipirona', true);
INSERT INTO paciente (id, nome, cpf, telefone, email, data_nascimento, sexo, observacoes, ativo) VALUES
  (3, 'Roberto Souza', '333.444.555-66', '(11) 8888-3333', 'roberto@email.com', '1978-11-05', 'M', '', true);

-- Consultas
INSERT INTO consulta (id, paciente_id, medico_id, data_hora, status, motivo, observacoes) VALUES
  (1, 1, 1, '2025-07-10 09:00:00', 'AGENDADA', 'Consulta de rotina', '');
INSERT INTO consulta (id, paciente_id, medico_id, data_hora, status, motivo, observacoes) VALUES
  (2, 2, 2, '2025-07-10 10:30:00', 'AGENDADA', 'Dor no peito', '');
INSERT INTO consulta (id, paciente_id, medico_id, data_hora, status, motivo, observacoes) VALUES
  (3, 3, 1, '2025-07-09 14:00:00', 'REALIZADA', 'Dor de cabeça frequente', 'Prescrito analgésico.');

-- Ficha de atendimento
INSERT INTO ficha_atendimento (id, consulta_id, diagnostico, prescricao, exames_solicitados, observacoes_medico, data_registro) VALUES
  (1, 3, 'Enxaqueca tensional', 'Dipirona 500mg - 1 comprimido a cada 6h por 5 dias', 'Hemograma completo', 'Paciente orientado sobre hidratação e sono', '2025-07-09 15:30:00');
