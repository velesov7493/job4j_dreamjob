function prepareSave() {
    let checkFld = $('#name');
    if (checkFld.val() === '') {
        alert(checkFld.attr('title'));
        return false;
    }
    checkFld = $('#description');
    if (checkFld.val() === '') {
        alert(checkFld.attr('title'));
        return false;
    }
}