INSERT INTO tb_role (id, authority) VALUES ('550e8400-e29b-41d4-a716-446655440000', 'ROLE_ADMIN');
INSERT INTO tb_role (id, authority) VALUES ('550e8400-e29b-41d4-a716-446655440001', 'ROLE_USER');

INSERT INTO tb_user (id, email, password, deleted_at)VALUES ('550e8400-e29b-41d4-a716-446655440002', 'john.doe@example.com', '$2y$10$9qmyuurKSrcP4TxviJfdLeJds0JZEGCf3m8LpFLIojqF82KfOsyLW', NULL);
INSERT INTO tb_user (id, email, password, deleted_at)VALUES ('550e8400-e29b-41d4-a716-446655440003', 'noah.lourenco@example.com', '$2y$10$9qmyuurKSrcP4TxviJfdLeJds0JZEGCf3m8LpFLIojqF82KfOsyLW', NULL);
INSERT INTO tb_user (id, email, password, deleted_at)VALUES ('550e8400-e29b-41d4-a716-446655440004', 'amanda.lourenco@example.com', '$2y$10$9qmyuurKSrcP4TxviJfdLeJds0JZEGCf3m8LpFLIojqF82KfOsyLW', '2024-02-01 10:00:00');

INSERT INTO tb_user_role (user_id, role_id)VALUES ('550e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440000');
INSERT INTO tb_user_role (user_id, role_id)VALUES ('550e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655440000');
INSERT INTO tb_user_role (user_id, role_id)VALUES ('550e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655440001');
