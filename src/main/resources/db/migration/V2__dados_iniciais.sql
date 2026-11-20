-- V2: dados iniciais para testes (coleção do Postman)
-- Os IDs são gerados automaticamente na ordem de inserção:
-- 1º livro = LIV-1, 2º livro = LIV-2, e assim por diante.

INSERT INTO tb_livro (titulo, autor, isbn, quantidade_estoque) VALUES
    ('SOA na Prática', 'Nicolai Josuttis', '978-85-7608-251-4', 3),
    ('Cloud Computing: Concepts, Technology & Architecture', 'Thomas Erl', '978-00-0000-002-0', 0),
    ('Mastering Cloud Computing', 'Rajkumar Buyya', '978-00-0000-003-0', 2);

INSERT INTO tb_cliente (matricula, nome_aluno, idade_aluno) VALUES
    ('2026001', 'Maria Souza', 21),
    ('2026002', 'Carlos Lima', 23);