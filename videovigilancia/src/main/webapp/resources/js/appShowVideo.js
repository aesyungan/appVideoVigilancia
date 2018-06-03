$(document).ready(function () {

    //inicializar variables
    var video;
    var width;
    var height;
    var canvas;
    var canvasG;
    var images = [];
    var ctx;
    var ctxG;
    var result;
    var capture;
    var loopnum;
    var startTime;
    var capturing = false;
    var msgdiv;
    var progress;
    var startButton;
    var stopButton;
    var ws = null;
    init();

    //end inicializar variables
    // var video = $("#video").get()[0];
    // console.log(video);
    // var canvas = $("#canvas");
    // var ctx = canvas.get()[0].getContext('2d');
    //img en video
    //funciones
    function connectarServer() {
        var host = $('#hot-socket').val();
        console.log("connecting to +>" + host);
        ws = new WebSocket("ws://" + host + "/videovigilancia/livevideo");
        ws.onmessage = (msg) => {
            //imagen
            //console.log(msg.data);
            var img = new Image();

            img.onload = function () {
                var w = img.width;
                var h = img.height;
                ctx.drawImage(img, 0, 0, 320, 240);//crea de este tamaño en el canvas
                ctxG.drawImage(img, 0, 0, 320, 240);//crea de este tamaño en el canvas
            };
            img.src = URL.createObjectURL(msg.data);
        }
    }
    function init() {
        // video = document.getElementById('campreview');
        // canvas = document.createElement('canvas');
        //ctx = canvas.getContext('2d');
        canvas = $("#canvas");
        canvasG = $("#canvas_grav");
        ctx = canvas.get()[0].getContext('2d');
        ctxG = canvasG.get()[0].getContext('2d');
        result = document.getElementById('result');
        msgdiv = document.getElementById('information');
        progress = document.getElementById('progress');
        startButton = document.getElementById('btnStartButton');
        stopButton = document.getElementById('btnStopButton');
        connectarServer();
    }
    //butones
    $("#btnStartButton").click(function () {
        startCapture();
    });

    $("#btnStopButton").click(function () {
        stopCapture();
    });
    $("#btn-conectar").click(function () {

        connectarServer();
    });
    /**
     * Capture the next frame of the video.
     */
    function nextFrame() {
        if (capturing) {
            var imageData;
            //ctx.drawImage(video, 0, 0, width, height);
            var ctx2 = ctxG;
            imageData = ctx2.getImageData(0, 0, canvasG.width(), canvasG.height());
            images.push({duration: new Date().getTime() - startTime, datas: imageData});
            startTime = new Date().getTime();
            requestAnimationFrame(nextFrame);
        } else {
            requestAnimationFrame(finalizeVideo);
        }

    }

    /**
     * Start the encoding of the captured frames.
     */
    function finalizeVideo() {
        var capture = new Whammy.Video();
        setMessage('Encoding video...');
        progress.max = images.length;
        showProgress(true);
        encodeVideo(capture, 0);
    }

    /**
     * Encode the captured frames.
     * 
     * @param capture
     * @param currentImage
     */
    function encodeVideo(capture, currentImage) {
        if (currentImage < images.length) {
            ctxG.putImageData(images[currentImage].datas, 0, 0);
            capture.add(ctxG, images[currentImage].duration);
            delete images[currentImage];
            progress.value = currentImage;
            currentImage++;
            setTimeout(function () {
                encodeVideo(capture, currentImage);
            }, 5);
        } else {
            var output = capture.compile();
            result.src = window.URL.createObjectURL(output);
            setMessage('Finished');
            images = [];
            enableStartButton(true);
        }
    }

    /**
     * Initialize the canvas' size with the video's size.
     */
    function initSize() {
        //width = video.clientWidth;
        //height = video.clientHeight;
        width = canvasG.width;
        height = canvasG.height;
        // canvas.width = width;
        // canvas.height = height;
    }

    /**
     * Initialize the css style of the buttons and the progress bar
     * when capturing.
     */
    function initStyle() {
        setMessage('Capturing...');
        showProgress(false);
        enableStartButton(false);
        enableStopButton(true);
    }

    /**
     * Start the video capture.
     */
    function startCapture() {
        initSize();
        initStyle();
        capturing = true;
        startTime = new Date().getTime();
        nextFrame();
    }

    /**
     * Stop the video capture.
     */

    function stopCapture() {
        capturing = false;
        enableStopButton(false);
    }

    /* *************************************************************
     *                   Styles functions
     * *************************************************************/

    /**
     * Enable/Disable the start button.
     */
    function enableStartButton(enabled) {
        startButton.disabled = !enabled;
    }

    /**
     * Enable/Disable the stop button.
     */
    function enableStopButton(enabled) {
        stopButton.disabled = !enabled;
    }

    /**
     * Display/Hide the progress bar.
     */
    function showProgress(show) {
        progress.style.visibility = show ? 'visible' : 'hidden';
    }

    /**
     * Display a message in the msgdiv block.
     */
    function setMessage(message) {
        msgdiv.innerHTML = message;
    }
});