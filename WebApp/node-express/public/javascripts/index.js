var bucketUrl = "https://s3.amazonaws.com/rekognition-image-102062329/"

$(function(){				
    $('#recipebtn').click(function(e){
        $('#recipebtn').css('background-color','#008b8b');
        $('#spacebtn').css('background-color','transparent');
        requestRecipe('["1","2"]');
        rendarRecipe();
        //$('#main').html(itemList);
    });				
});


 $(function(){				
    $('#spacebtn').click(function(e){
        $('#spacebtn').css('background-color','#008b8b');
        $('#recipebtn').css('background-color','transparent');
        e.preventDefault();
        var data = {};
        data.title = "title";
        data.message = "message";
        
        $.ajax({
          type: 'POST',
          data: JSON.stringify(data),
          contentType: 'application/json',
          url: './space',						
          success: function(data) {
              console.log('success');
              console.log(JSON.stringify(data));
              var itemList = "";
              for(key in data){
                itemList += getHtml([
                  '<div class="row item" data-toggle="modal" data-target="#myModal" onclick=\'createItemModal("'+data[key]["reko_result"]+'","'+data[key]["imagename"]+'")\'>',
                  '<div class="col-sm-6">'+data[key]["reko_result"]+'</div>',
                  '<div class="col-sm-6 item-btn-group"><button type="button" class="btn">Rename</button>&nbsp;&nbsp;<button type="button" class="btn btn-warning">Set Expiration Time</button></div>',
                  '</div>'
                ])
              }
              $('#main').html(itemList);
              //$('#menu').css('display','none');
          }
        });
    });				
});







