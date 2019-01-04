var switchStrict=0;

$("#nb1").on("click",function(){
    if(switchStrict===0){
        $(this).css("background","rgb(255, 0, 192)");
        switchStrict=1;
    }else{
        $(this).css("background","brown");
        switchStrict=0;
    }
})