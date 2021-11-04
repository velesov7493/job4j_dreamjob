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
        type: 'POST',
        url: 'http://localhost:8080/dreamjob/greet',
        data: JSON.stringify({
            name: $('#email').val()
        }),
        dataType: 'json'
    }).done(function (data) {
        $('#emailList li:last').append('<li>' + data.name + '</li>');
    }).fail(function (err) {
        console.log(JSON.stringify(err));
    });
}

$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/dreamjob/greet',
        dataType: 'json'
    }).done(function (data) {
        for (var email of data) {
            $('#emailList li:last').append('<li>' + email.name + '</li>')
        }
    }).fail(function (err) {
        console.log(JSON.stringify(err));
    });
});