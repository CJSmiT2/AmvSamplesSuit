$(function() {
    refresh_cut_info();
    setInterval('refresh_cut_info()', 5000);
});

function refresh_cut_info() {
    jQuery.ajaxSetup({async:false});
    $.ajax({
            type: 'get',
            url: 'get_current_cut_info',
            success: function(params){
                console.log("params: " + params);
                if (params != null) {
                    setCutInfo(params);
                }
            },
            error: function(err){
                console.log(err);
            }
        });
}

function setCutInfo(params){
    console.log(params);

    var inProgress = getParamValue(params, "inProgress");
    if (inProgress === 'true'){
        var fileName = getParamValue(params, "fileName");
        var samplesCount = getParamValue(params, "samplesCount");
        var ss = getParamValue(params, "ss");
        var lastGifPath = getParamValue(params, "lastGifPath");

        $("#fileName").empty();
        $("#fileName").text(fileName);
        $("#ss").empty();
        $("#ss").text(secondsToHms(ss));
        $("#samplesCount").empty();
        $("#samplesCount").text(samplesCount);
        $("#lastGif").attr("src", "/last_gif?path=" + lastGifPath);
        $("#process").css("display", "block");

    } else {
        $("#process").css("display", "none");
    }
}

function getParamValue(params, searchName){
    var paramCouple = params.split("&");
    for (var i = 0; i < paramCouple.length; i++) {
        var splited = paramCouple[i].split("=");
        var name = splited[0];
        var value = splited[1];

        if (name == searchName){
            return value;
        }
    }
    return null;
}

function selectItem(id) {
    if (document.getElementById(id).checked == false) {
        document.getElementById(id).checked = true;
    } else {
        document.getElementById(id).checked = false;
    }
}

function secondsToHms(d) {
    d = Number(d);
    var h = Math.floor(d / 3600);
    var m = Math.floor(d % 3600 / 60);
    var s = Math.floor(d % 3600 % 60);
    
    if (h < 10) {
        h = "0" + h;
    }
    if (m < 10) {
        m = "0" + m;
    }
    if (s < 10) {
        s = "0" + s;
    }

    return h + ":" + m + ":" + s; 
}

function showSamplesGroupsToForm(){
    var option = $("#samples_action").val();
    
    if (option == "add_to_samples_group"){
        $("#samples_group").css("display", "block");
    } else {
        $("#samples_group").css("display", "none");
    }
    
    if (option == "add_folder_to_titles_group"){
        $("#title_group").css("display", "block");
    } else {
        $("#title_group").css("display", "none");
    }
    
    if (option == "delete_samples"){
        $("#delete_samples").css("display", "block");
    } else {
        $("#delete_samples").css("display", "none");
    }
}


