var albumBucketName = 'rekognition-image-102062329';

function getHtml(template) {
  return template.join('\n');
}

function createItemModal(reko_result, imagename){
	var i, footer;
  var photoUrl = bucketUrl + encodeURIComponent(imagename);   
	$('h4.modal-title').html(reko_result);
	$('div.modal-body').html("<img style='width:100%' src='"+photoUrl+"''>");
  var footer = '<button type="button" class="btn btn">Rename</button>&nbsp;&nbsp;<button type="button" class="btn btn-warning">Set Expiration Time</button>';
	$('div.modal-footer').html(footer);
}


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