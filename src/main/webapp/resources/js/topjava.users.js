const userAjaxUrl = "admin/users/";


const ctx = {
    ajaxUrl: userAjaxUrl,
    updateTable: function () {
        $.get(userAjaxUrl, updateTableByData);
    }
}

function enable(checkbox, id) {
    const enabled = checkbox.is(":checked");
    $.ajax({
        url: userAjaxUrl + id,
        type: "POST",
        data: "enabled=" + enabled
    }).done(function () {
        checkbox.closest("tr").attr("data-user-enabled", enabled);
        successNoty(enabled ? "Enabled" : "Disabled");
    }).fail(function () {
        $(checkbox).prop("checked", !enabled);
    });
}

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    );
});