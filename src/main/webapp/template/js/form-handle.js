function validate() {
    var checkFld = $('#fname');
    if (checkFld.val() === '') {
        alert(checkFld.attr('title'));
        return false;
    }
    checkFld = $('#lname');
    if (checkFld.val() === '') {
        alert(checkFld.attr('title'));
        return false;
    }
    checkFld = $('#description');
    if (checkFld.val() === '') {
        alert(checkFld.attr('title'));
        return false;
    }
    return true;
}

function addRow() {
    var fName = $('#fname').val();
    var lName = $('#lname').val();
    var num = $('#list >tbody >tr').length;

    $('#list tr:last').after('<tr><td scope="row">' + (num + 1) + '</td><td>' + fName + '</td><td>' + lName + '</td><td>@anonymous</td></tr>');
}

function sendGreeting() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/dreamjob/greet',
        data: 'name=' + $('#email').val(),
        dataType: 'text'
    }).done(function(data) {
        $('#ajax-form').before('<h3>' + data + '</h3>');
    }).fail(function(err) {
        alert(err);
    });
}