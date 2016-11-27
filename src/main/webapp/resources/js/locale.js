PrimeFaces.locales['pt'] = {  
    closeText: 'Fechar',  
    prevText: 'Anterior',  
    nextText: 'Próximo',  
    currentText: 'Começo',  
    monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],  
    monthNamesShort: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],  
    dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],  
    dayNamesShort: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],  
    dayNamesMin: ['D','S','T','Q','Q','S','S'],  
    weekHeader: 'Semana',  
    firstDay: 1,  
    isRTL: false,  
    showMonthAfterYear: false,  
    yearSuffix: '',  
    timeOnlyTitle: 'Só Horas',  
    timeText: 'Tempo',  
    hourText: 'Hora',  
    minuteText: 'Minuto',  
    secondText: 'Segundo',  
    currentText: 'Data Atual',  
    ampm: false,  
    month: 'Mês',  
    week: 'Semana',  
    day: 'Dia',  
    allDayText : 'Todo Dia'  
}; 
	
$( document ).ready(function() {
	$('.js-toggle').bind('click', function() {
		$('.js-sidebar').toggleClass('is-toggled');
		$('.js-content').toggleClass('is-toggled');
	});
});
