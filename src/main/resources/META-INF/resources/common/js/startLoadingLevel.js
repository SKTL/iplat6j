//加载loading效果
var content = ""

function startLoading(name, content) {
    this.content = content;
    timer = setInterval(`loadingPrint(${name})`, 1000);
}

function loadingPrint(name) {
    console.log(content)
    if (dian >= 3) {
        // document.getElementById(name).innerText="正在导出";
        name.innerText = content;
        dian = 0;
    } else {
        dian++;
        // var text = document.getElementById(name).innerText;
        // document.getElementById(name).innerText= text +' ●';
        var text = name.innerText;
        name.innerText = text + ' ●';
    }
}

//移除loading效果
function completeLoading(name, content) {
    clearInterval(timer);
    document.getElementById(name).innerText = content;
    // name.innerText = "确认";
}