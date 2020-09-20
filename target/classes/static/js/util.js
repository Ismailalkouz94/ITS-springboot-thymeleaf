function toastSuccess(message) {
    iziToast.show({
        title: 'Success',
        message: message,
        position: 'topRight',
        color: 'green'
    });
}

function toastError(message) {
    iziToast.show({
        title: 'Fail',
        message: message,
        position: 'topRight',
        color: 'red'
    });
}
