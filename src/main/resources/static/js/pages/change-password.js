$("#submitChangePasswordId").click(function (event) {
    event.preventDefault();
    $.post("/users/changePassword",
        {
            'userId': $('#userId').val(),
            'oldPassword': $('#oldPassword').val(),
            'newPassword': $('#newPassword').val(),
            'confirmPassword': $('#confirmPassword').val(),
        },
        function (response) {
            toastSuccess(response)
            $('#changePasswordId').trigger("reset");
        }).fail(function (response) {
        toastError(response.responseText)
    });
});

