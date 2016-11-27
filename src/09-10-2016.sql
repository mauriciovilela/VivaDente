INSERT INTO `db_vivadente`.`tb_funcionalidade` (`id`, `DS_FUNCIONALIDADE`, `DS_PAGINA`, `FL_VISIVEL`, `NR_ORDEM`) VALUES ('18', 'Laboratorio Histórico Modal', '/restrita/laboratorio/LaboratorioHistoricoMD', '0', '18');

UPDATE `db_vivadente`.`tb_funcionalidade` SET `NR_ORDEM`='17' WHERE `id`='17';

INSERT INTO `db_vivadente`.`tb_funcionalidade_perfil` (`DS_PERFIL`, `ID_FUNCIONALIDADE`) VALUES ('SOCIO', '18');

INSERT INTO `db_vivadente`.`tb_funcionalidade_perfil` (`DS_PERFIL`, `ID_FUNCIONALIDADE`) VALUES ('SECRETARIA', '18');

UPDATE `db_vivadente`.`tb_agenda_status` SET `DS_NOME`='Falta Dentista' WHERE `id`='4';

UPDATE `db_vivadente`.`tb_funcionalidade` SET `DS_FUNCIONALIDADE`='Laboratório', `DS_PAGINA`='/restrita/laboratorio/Laboratorio' WHERE `id`='16';

UPDATE `db_vivadente`.`tb_funcionalidade` SET `DS_FUNCIONALIDADE`='Laboratório Pesquisa', `DS_PAGINA`='/restrita/laboratorio/LaboratorioPesquisa' WHERE `id`='15';

UPDATE `db_vivadente`.`tb_funcionalidade` SET `NR_ORDEM`='40' WHERE `id`='13';

