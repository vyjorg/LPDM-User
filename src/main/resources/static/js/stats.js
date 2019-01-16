function orderStatsByYear(ctx, labels, data1, data2, data3){

    var myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: "Année en cours",
                data: data1,
                fill: false,
                borderColor: 'rgb(135, 227, 7)',
                backgroundColor: 'rgb(135, 227, 7)'
            },{
                label: "Année précédente",
                data: data2,
                fill: false,
                borderColor: 'rgb(47, 124, 202)',
                backgroundColor: 'rgb(47, 124, 202)'
            },{
                label: "Moyenne des 2 années",
                data: data3,
                fill: false,
                borderColor: 'rgb(221, 17, 119)',
                backgroundColor: 'rgb(221, 17, 119)'
            }]
        },
        options: {
            title: {
                display: true,
                text: "Statistique sur les commandes"
            },
            scales: {
                yAxes: [{
                    scaleLabel: {
                        display: true,
                        labelString: "Nombre de commandes"
                    },
                    ticks: {
                        beginAtZero:false
                    }
                }],
                xAxes: [{
                    scaleLabel: {
                        display: true,
                        labelString: "Mois de l'année"
                    }
                }]
            }
        }
    });
}