const video = document.getElementById("video");

navigator.mediaDevices.getUserMedia({
  video: { facingMode: "environment" }
}).then(stream => {
  video.srcObject = stream;
  video.play();
});

// ⚠️ SIMULACIÓN por ahora
// (luego puedes meter librería QR real)
setTimeout(() => {
  fetch("/validar?codigo=EPN12345")
    .then(r => r.text())
    .then(txt => {
      document.getElementById("resultado").innerText = txt;
    });
}, 3000);
