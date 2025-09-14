CREATE UNIQUE INDEX IF NOT EXISTS ux_inventory_run_item
ON inventory(run_id, item_id);

INSERT INTO item (id, item_name) VALUES
(101, 'item_백호'),
(102, 'item_주작'),
(103, 'item_청룡'),
(104, 'item_현무'),
(201, 'ghost_잡귀'),
(202, 'ghost_아귀'),
(203, 'ghost_어둑시니')


