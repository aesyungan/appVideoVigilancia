$(document).ready(function () {
//crear socket
    var ws = new WebSocket("ws://localhost:8080/videovigilancia/livevideo");//este es el cliente
    ws.onopen = function () {
        console.log("Openened connection to websocket");
    }
//datos del video
    var video = $("#live").get()[0];//recupera el id del video
    //var canvas = document.getElementById('canvas');
    //var ctx = canvas.getContext('2d');
    var canvas = $("#canvas");
    var ctx = canvas.get()[0].getContext('2d');
    var options = {
        "video": true
    };

// use the chrome specific GetUserMedia function
    navigator.getUserMedia = (navigator.getUserMedia || navigator.webkitGetUserMedia ||
            navigator.mozGetUserMedia || navigator.msGetUserMedia);
    navigator.getUserMedia(options, function (stream) {
        video.src = URL.createObjectURL(stream);
    }, function (err) {
        console.log("Unable to get video stream!")
    });
//se ejecutará cada 250 milisegundos y pintará el lienzo con la imagen tomada del elemento de video.
    timer = setInterval(
            function () {
                ctx.drawImage(video, 0, 0, 320, 240);//grafica la imagen en canvas
                var data = canvas.get()[0].toDataURL('image/jpeg', 1.0);
                newblob = convertToBinary(data);//convierte la imagen a binario
                ws.send(newblob);//envia la imagen por socket
                // console.log(newblob);
            }, 50);
    function convertToBinary(dataURI) {
        // convert base64 to raw binary data held in a string
        // doesn't handle URLEncoded DataURIs
        var byteString = atob(dataURI.split(',')[1]);

        // separate out the mime component
        var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]

        // write the bytes of the string to an ArrayBuffer
        var ab = new ArrayBuffer(byteString.length);
        var ia = new Uint8Array(ab);
        for (var i = 0; i < byteString.length; i++) {
            ia[i] = byteString.charCodeAt(i);
        }

        // write the ArrayBuffer to a blob, and you're done
        var bb = new Blob([ab]);
        return bb;
    }
});