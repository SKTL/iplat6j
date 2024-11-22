       var leftChild = document.getElementById('sidebar');
       var splitterLine = document.getElementById('line');
       var rightChild = document.getElementById('main-container');
       splitterLine.style.height = ($(window).height()-$('header-navbar').height())+'px';

       splitterLine.onmousedown = function(ev){
           var iEvent = ev||event;
           var dx = iEvent.clientX;//当你第一次单击的时候，存储x轴的坐标。//相对于浏览器窗口
           var leftWidth = leftChild.offsetWidth;
           var rightWidth = rightChild.offsetWidth;
           document.onmousemove = function(ev){
               var iEvent = ev||event;
               var diff = iEvent.clientX - dx;//移动的距离（向左滑时为负数,右滑时为正数）
               if(230 < (leftWidth + diff)  &&  230 < (rightWidth - diff)){
                   //两个div的最小宽度均为100px
                   leftChild.style.width = (leftWidth + diff) +'px';
                   rightChild.style.width = (rightWidth - diff) +'px';
                   splitterLine.style.left = leftChild.style.width;
               }
           };
           document.onmouseup=function(){
               document.onmousedown = null;
               document.onmousemove = null;
           };
           return false;
       }



