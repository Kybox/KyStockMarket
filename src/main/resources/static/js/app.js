var chart = new SmoothieChart(
    {
        grid: {
            strokeStyle: 'rgb(240, 240, 240)', fillStyle: 'rgb(255, 255, 255)',
            lineWidth: 1, millisPerLine: 250, verticalSections: 6
        },
        labels: { fillStyle:'rgb(0, 0, 0)' }
    }
);
chart.streamTo(document.getElementById("chart"), 500);
var flow = [];
var colors = [
    {
        strokeStyle: 'rgba(0, 255, 0, 1)',
        fillStyle: 'rgba(255, 255, 255, 0.2)',
        lineWidth: 1
    },
    {
        strokeStyle: 'rgba(255, 0, 0, 1)',
        fillStyle: 'rgba(255, 255, 255, 0.2)',
        lineWidth: 1
    },
    {
        strokeStyle: 'rgba(40, 96, 144, 1)',
        fillStyle: 'rgba(255, 255, 255, 0.2)',
        lineWidth: 1
    },
    {
        strokeStyle: 'rgba(0, 0, 0, 1)',
        fillStyle: 'rgba(255, 255, 255, 0.2)',
        lineWidth: 1
    }
];
var colorIndex = -1;
function onConnect(id){

    if(!flow[id]){
        flow[id] = new TimeSeries();
        chart.addTimeSeries(flow[id], randomColor());

        var connection = new EventSource("/transaction/stream/" + id);
        connection.onmessage = function (e) {

            var transactions = JSON.parse(e.data);
            document.getElementById("price" + id).innerHTML = transactions.price;
            flow[id].append(new Date().getTime(), transactions.price);
        };
    }
}

function randomColor() {

    ++ colorIndex;
    if(colorIndex > colors.length) colorIndex = 0;
    return colors[colorIndex];
}