$("#pic1").click(function(event) {
  event.preventDefault();
  $(this).addClass('active');
  $(this).siblings().removeClass('active');
  $("#dud1").addClass('active');
  $("#dud2, #dud3, #dud4, #dud5, #dud6").removeClass('active');

});

$("#pic2").click(function(event) {
  event.preventDefault();
  $(this).addClass('active');
  $(this).siblings().removeClass('active');
  $("#dud2").addClass('active');
  $("#dud1, #dud3, #dud4, #dud5, #dud6").removeClass('active');

});

$("#pic3").click(function(event) {
  event.preventDefault();
  $(this).addClass('active');
  $(this).siblings().removeClass('active');
  $("#dud3").addClass('active');
  $("#dud1, #dud2, #dud4, #dud5, #dud6").removeClass('active');

});

$("#pic4").click(function(event) {
  event.preventDefault();
  $(this).addClass('active');
  $(this).siblings().removeClass('active');
  $("#dud4").addClass('active');
  $("#dud1, #dud2, #dud3, #dud5, #dud6").removeClass('active');

});

$("#pic5").click(function(event) {
  event.preventDefault();
  $(this).addClass('active');
  $(this).siblings().removeClass('active');
  $("#dud5").addClass('active');
  $("#dud1, #dud2, #dud3, #dud4, #dud6").removeClass('active');

});

$("#pic6").click(function(event) {
  event.preventDefault();
  $(this).addClass('active');
  $(this).siblings().removeClass('active');
  $("#dud6").addClass('active');
  $("#dud1, #dud2, #dud3, #dud4, #dud5").removeClass('active');

});