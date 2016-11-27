INSERT INTO tb_funcionalidade (`id`, `DS_FUNCIONALIDADE`, `DS_PAGINA`, `FL_VISIVEL`, `ID_PAI`, `NR_ORDEM`) VALUES ('19', 'Relatório de Pagamentos', '/restrita/relatorio/RptPagamentos', '0', '13', '19');

INSERT INTO tb_funcionalidade_perfil (`DS_PERFIL`, `ID_FUNCIONALIDADE`) VALUES ('SOCIO', '19');
INSERT INTO tb_funcionalidade_perfil (`DS_PERFIL`, `ID_FUNCIONALIDADE`) VALUES ('SECRETARIA', '19');

UPDATE tb_funcionalidade SET `DS_ICONE`='fa fa-fw fa-bar-chart' WHERE `id`='19';
