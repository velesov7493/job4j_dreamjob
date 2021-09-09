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