//加载loading效果
function startLoading(name) {
    timer = setInterval(`loadingPrint(${name})`,1000);
}
function loadingPrint(name) {
    if (dian>=3){
        // document.getElementById(name).innerText="正在导出";
        name.innerText= "正在确认";
        dian=0;
    }else{
        dian++;
        // var text = document.getElementById(name).innerText;
        // document.getElementById(name).innerText= text +' ●';
        var text = name.innerText;
        name.innerText = text +' ●';
    }
}
//移除loading效果
function completeLoading(name) {
    clearInterval(timer);
    document.getElementById(name).innerText="确认";
    // name.innerText = "确认";
}