$(function() {
    refresh_export_info();
    setInterval('refresh_export_info()', 5000);
});

function refresh_export_info() {
    jQuery.ajaxSetup({async:false});
    $.ajax({
            type: 'get',
            url: 'get_current_export_queue_size',
            success: function(exportSize){
                console.log("export size: " + exportSize);
                setExportInfo(exportSize)
            },
            error: function(err){
                console.log(err);
            }
        });
}

function setExportInfo(exportSize){
    if (exportSize > 0){
        $("#exportSize").empty();
        $("#exportSize").text(exportSize);
        
        $("#exportInfo").css("display", "block");

    } else {
        $("#exportInfo").css("display", "none");
    }
}
