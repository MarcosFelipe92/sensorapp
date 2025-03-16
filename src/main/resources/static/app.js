const stompClient = new StompJs.Client({
  brokerURL: "ws://" + window.location.host + "/sensor",
});

stompClient.onConnect = (frame) => {
  console.log("✅ Conectado ao WebSocket: " + frame);

  stompClient.subscribe("/main/params", (message) => {
    updateSensorData(JSON.parse(message.body));
  });
};

stompClient.onWebSocketError = (error) => {
  console.error("🚨 Erro com o WebSocket", error);
};

stompClient.onStompError = (frame) => {
  console.error("❌ Erro do WebSocket: " + frame.headers["message"]);
  console.error("Detalhes: " + frame.body);
};

function connect() {
  stompClient.activate();
}

function disconnect() {
  stompClient.deactivate();
  console.log("🔌 Desconectado");
}

function updateSensorData(data) {
  $("#temperature-data").text(data.temperatureMessage);
  $("#luminosity-data").text(data.luminosityMessage);
  $("#humidity-data").text(data.humidityMessage);
}

$(document).ready(() => {
  connect();
});
