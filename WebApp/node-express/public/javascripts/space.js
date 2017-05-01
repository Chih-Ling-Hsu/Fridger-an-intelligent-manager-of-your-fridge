var albumBucketName = 'rekognition-image-102062329';
var bucketUrl = "https://s3.amazonaws.com/rekognition-image-102062329/"

function getHtml(template) {
  return template.join('\n');
}

function createModal(reko_result, imagename){
	var i, footer;
  var photoUrl = bucketUrl + encodeURIComponent(imagename);   
	$('h4.modal-title').html(reko_result);
	$('div.modal-body').html("<img style='width:100%' src='"+photoUrl+"''>");
  var footer = '<button type="button" class="btn btn">Rename</button>&nbsp;&nbsp;<button type="button" class="btn btn-warning">Set Expiration Time</button>';
	$('div.modal-footer').html(footer);
}

 $(function(){				
    $('#show_space').click(function(e){
        $('#spacebtn').css('background-color','#008b8b');
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
                  '<div class="row item" data-toggle="modal" data-target="#myModal" onclick=\'createModal("'+data[key]["reko_result"]+'","'+data[key]["imagename"]+'")\'>',
                  '<div class="col-sm-8">'+data[key]["reko_result"]+'</div>',
                  '<div class="col-sm-4 item-btn-group"><button type="button" class="btn">Rename</button>&nbsp;&nbsp;<button type="button" class="btn btn-warning">Set Expiration Time</button></div>',
                  '</div>'
                ])
              }
              $('#list').html(itemList);
              //$('#menu').css('display','none');
          }
        });
    });				
});




var s3 = new AWS.S3({
  apiVersion: '2006-03-01',
  params: {Bucket: albumBucketName}
});


function getBucketURL() {
  s3.listObjects({Delimiter: '.jpg'}, function(err, data) {
    if (err) {
      return alert('There was an error viewing your album: ' + err.message);
    }
    // `this` references the AWS.Response instance that represents the response
    var href = this.request.httpRequest.endpoint.href;
    bucketUrl = href + albumBucketName + '/';  
  });
}


//getBucketURL();




function listItems() {
  s3.listObjects({Delimiter: '.jpg'}, function(err, data) {
    if (err) {
      return alert('There was an error listing your Items: ' + err.message);
    } else {
      var items = data.CommonPrefixes.map(function(commonPrefix) {
        var prefix = commonPrefix.Prefix;
        var itemName = decodeURIComponent(prefix.replace('/', ''));
        return getHtml([
          '<li>',
            '<span onclick="deleteItem(\'' + itemName + '\')">X</span>',
            '<span onclick="viewItem(\'' + itemName + '\')">',
              itemName,
            '</span>',
          '</li>'
        ]);
      });
      var message = items.length ?
        getHtml([
          '<p>Click on an item name to view it.</p>',
          '<p>Click on the X to delete the item.</p>'
        ]) :
        '<p>You do not have any items. Please Create item.';
      var htmlTemplate = [
        '<h2>Lists</h2>',
        message,
        '<ul>',
          getHtml(items),
        '</ul>',
        '<button onclick="createAlbum(prompt(\'Enter Album Name:\'))">',
          'Create New Album',
        '</button>'
      ]
      document.getElementById('app').innerHTML = getHtml(htmlTemplate);
    }
  });
}

function createAlbum(albumName) {
  albumName = albumName.trim();
  if (!albumName) {
    return alert('Album names must contain at least one non-space character.');
  }
  if (albumName.indexOf('/') !== -1) {
    return alert('Album names cannot contain slashes.');
  }
  var albumKey = encodeURIComponent(albumName) + '/';
  s3.headObject({Key: albumKey}, function(err, data) {
    if (!err) {
      return alert('Album already exists.');
    }
    if (err.code !== 'NotFound') {
      return alert('There was an error creating your album: ' + err.message);
    }
    s3.putObject({Key: albumKey}, function(err, data) {
      if (err) {
        return alert('There was an error creating your album: ' + err.message);
      }
      alert('Successfully created album.');
      viewAlbum(albumName);
    });
  });
}

function viewAlbum(albumName) {
  var albumPhotosKey = encodeURIComponent(albumName) + '//';
  s3.listObjects({Prefix: albumPhotosKey}, function(err, data) {
    if (err) {
      return alert('There was an error viewing your album: ' + err.message);
    }
    // `this` references the AWS.Response instance that represents the response
    var href = this.request.httpRequest.endpoint.href;
    var bucketUrl = href + albumBucketName + '/';

    var photos = data.Contents.map(function(photo) {
      var photoKey = photo.Key;
      var photoUrl = bucketUrl + encodeURIComponent(photoKey);
      return getHtml([
        '<span>',
          '<div>',
            '<img style="width:128px;height:128px;" src="' + photoUrl + '"/>',
          '</div>',
          '<div>',
            '<span onclick="deletePhoto(\'' + albumName + "','" + photoKey + '\')">',
              'X',
            '</span>',
            '<span>',
              photoKey.replace(albumPhotosKey, ''),
            '</span>',
          '</div>',
        '<span>',
      ]);
    });
    var message = photos.length ?
      '<p>Click on the X to delete the photo</p>' :
      '<p>You do not have any photos in this album. Please add photos.</p>';
    var htmlTemplate = [
      '<h2>',
        'Album: ' + albumName,
      '</h2>',
      message,
      '<div>',
        getHtml(photos),
      '</div>',
      '<input id="photoupload" type="file" accept="image/*">',
      '<button id="addphoto" onclick="addPhoto(\'' + albumName +'\')">',
        'Add Photo',
      '</button>',
      '<button onclick="listAlbums()">',
        'Back To Albums',
      '</button>',      
    ]
    document.getElementById('app').innerHTML = getHtml(htmlTemplate);
  });
}

function addPhoto(albumName) {
  var files = document.getElementById('photoupload').files;
  if (!files.length) {
    return alert('Please choose a file to upload first.');
  }
  var file = files[0];
  var fileName = file.name;
  var albumPhotosKey = encodeURIComponent(albumName) + '//';

  var photoKey = albumPhotosKey + fileName;
  s3.upload({
    Key: photoKey,
    Body: file,
    ACL: 'public-read'
  }, function(err, data) {
    if (err) {
      return alert('There was an error uploading your photo: ', err.message);
    }
    alert('Successfully uploaded photo.');
    viewAlbum(albumName);
  });
}

function deletePhoto(albumName, photoKey) {
  s3.deleteObject({Key: photoKey}, function(err, data) {
    if (err) {
      return alert('There was an error deleting your photo: ', err.message);
    }
    alert('Successfully deleted photo.');
    viewAlbum(albumName);
  });
}

function deleteAlbum(albumName) {
  var albumKey = encodeURIComponent(albumName) + '/';
  s3.listObjects({Prefix: albumKey}, function(err, data) {
    if (err) {
      return alert('There was an error deleting your album: ', err.message);
    }
    var objects = data.Contents.map(function(object) {
      return {Key: object.Key};
    });
    s3.deleteObjects({
      Delete: {Objects: objects, Quiet: true}
    }, function(err, data) {
      if (err) {
        return alert('There was an error deleting your album: ', err.message);
      }
      alert('Successfully deleted album.');
      listAlbums();
    });
  });
}