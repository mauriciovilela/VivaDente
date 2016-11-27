
-- Doris
delete from tb_pagamento where id = 52; 
-- Lindalra
delete from tb_pagamento_historico where id_pagamento = 69; 
delete from tb_pagamento where id = 69; 
-- Laura Meire
delete from tb_pagamento where id in (47,48); 
-- Tassiano
delete from tb_pagamento where id = 17; 

INSERT INTO tb_funcionalidade (id, DS_ICONE, DS_FUNCIONALIDADE, DS_PAGINA, FL_VISIVEL, ID_PAI, NR_ORDEM) VALUES ('20', 'fa fa-fw fa-bar-chart', 'Relatório de Pagamentos - Laboratório', '/restrita/relatorio/RptPagamentosLab', '0', '13', '20');
UPDATE tb_funcionalidade SET DS_FUNCIONALIDADE='Relatório de Pagamentos - Pacientes' WHERE id='19';

INSERT INTO tb_funcionalidade_perfil (DS_PERFIL, ID_FUNCIONALIDADE) VALUES ('SECRETARIA', '20');
INSERT INTO tb_funcionalidade_perfil (DS_PERFIL, ID_FUNCIONALIDADE) VALUES ('SOCIO', '20');

UPDATE tb_funcionalidade SET DS_ICONE='fa fa-fw fa-dollar' WHERE id='19';
UPDATE tb_funcionalidade SET DS_ICONE='fa fa-fw fa-dollar' WHERE id='20';

/*
	--verificar com HUDSON (Cliente DANILO do Laboratório)
	delete from tb_laboratorio where id in (9,15);
*/
