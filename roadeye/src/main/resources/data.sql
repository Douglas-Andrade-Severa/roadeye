--Insert Schools
INSERT IGNORE INTO tb_school(school_id, name, status) values(1, 'Senac', 'ACTIVATE');
INSERT IGNORE INTO tb_school(school_id, name, status) values(2, 'Senai', 'ACTIVATE');
INSERT IGNORE INTO tb_school(school_id, name, status) values(3, 'Unifebe', 'ACTIVATE');
--Insert address
INSERT IGNORE INTO tb_address(address_id,city,complement,country,latitude,longitude,neighborhood,number,post_code,state,status,street) VALUES (1,'Brusque','Em frente Havan'    ,'Brasil','-27.098901191795832','-48.90717266709429' ,'Centro 2'        ,'191','88353-100','SC','ACTIVATE','Rod. Antônio Heil');
INSERT IGNORE INTO tb_address(address_id,city,complement,country,latitude,longitude,neighborhood,number,post_code,state,status,street) VALUES (2,'Brusque','Primeiro de Maio'   ,'Brasil','-27.1142133029583'  ,'-48.91024731046353' ,'Primeiro de Maio','670','88353-202','SC','ACTIVATE','Av. 1 de Maio');
INSERT IGNORE INTO tb_address(address_id,city,complement,country,latitude,longitude,neighborhood,number,post_code,state,status,street) VALUES (3,'Brusque','Em frente beira rio','Brasil','-27.06680656426497' ,'-48.885325348732344','Santa Terezinha' ,'333','88352-360','SC','ACTIVATE','R. Vendelino Maffezzolli');
--Insert relationship between school and address
INSERT IGNORE INTO tb_school_address(id,address_id,school_id) VALUES (1,1,1);
INSERT IGNORE INTO tb_school_address(id,address_id,school_id) VALUES (2,2,2);
INSERT IGNORE INTO tb_school_address(id,address_id,school_id) VALUES (3,3,3);
