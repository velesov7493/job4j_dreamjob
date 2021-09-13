$(document).ready(function() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/dreamjob/cities',
        dataType: 'json'
    }).done(function (data) {
        const cityId = $('#selectedCityId').val();
        for (var city of data) {
            $('#cityList option:last').after('<option id="' + city.id + '">' + city.name + '</option>');
        }
        if (!(cityId === '' || isNaN(cityId))) {
            $('#cityList >option#'+cityId).attr('selected', 1);
        }
    }).fail(function (err) {
        console.log(JSON.stringify(err));
    });
});

function prepareSave() {
    let checkFld = $('#name');
    if (checkFld.val() === '') {
        alert(checkFld.attr('title'));
        return false;
    }
    checkFld = $('#position');
    if (checkFld.val() === '') {
        alert(checkFld.attr('title'));
        return false;
    }
    const cityId = $('#cityList').children(':selected').attr('id');
    $('#selectedCityId').val(cityId);
}