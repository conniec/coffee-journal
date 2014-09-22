$(function() {

    // Submit the new coffee form
    function submitForm() {
        console.log("submitting form");
        var data = retrieveInputs();
        console.log(data);

        $.ajax({
            type: "POST",
            url: "/newCoffee",
            data: data,
            dataType: "json"
        });

    }

    // Retrieve the inputs from the input boxes
    function retrieveInputs() {
        var inputs = {};
        inputs['name'] = $('#name').val();
        inputs['roaster'] = $('#roaster').val();
        inputs['roastDate'] = $('#roastDate').val();
        inputs['producer'] = $('#producer').val();
        inputs['brewDate'] = $('#brewDate').val();
        inputs['price'] = $('#price').val();
        inputs['rating'] = $('#rating').val();

        return inputs;
    }

    // Add listener to the submit button
    $('#submit').click(function() {
        submitForm();
    });

});