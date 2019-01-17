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

function orderedProductsStatsByYear(ctx, labels, data1, data2, data3){

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
                borderDash: [5, 5],
                borderColor: 'rgb(221, 17, 119)',
                backgroundColor: 'rgb(221, 17, 119)'
            }]
        },
        options: {
            title: {
                display: true,
                text: "Statistique sur les produits commandés par années"
            },
            scales: {
                yAxes: [{
                    scaleLabel: {
                        display: true,
                        labelString: "Nombre de produits commandés"
                    },
                    ticks: {
                        beginAtZero:false
                    }
                }],
                xAxes: [{
                    scaleLabel: {
                        display: false,
                        labelString: "Mois de l'année"
                    }
                }]
            }
        }
    });
}

function orderedProductsStatsByYearAndCategory(ctx, labels, data, title){

    var myChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: labels,
            datasets: [{
                label: "Année en cours",
                data: data,
                fill: false,
                backgroundColor: [
                    "rgb(22, 160, 133)",
                    "rgb(211, 84, 0)",
                    "rgb(41, 128, 185)",
                    "rgb(231, 76, 60)",
                    "rgb(52, 73, 94)",
                    "rgb(243, 156, 18)",
                    "rgb(155, 89, 182)"
                ]
            }]
        },
        options: {
            responsive: true,
            title: {
                display: true,
                position: 'bottom',
                text: title
            },
            legend: {
                display: false
            },
            animation: {
                animateScale: true,
                animateRotate: true
            }
            /*
            scales: {
                yAxes: [{
                    scaleLabel: {
                        display: false,
                        labelString: "Nombre de produits commandés"
                    },
                    ticks: {
                        beginAtZero:false
                    }
                }],
                xAxes: [{
                    scaleLabel: {
                        display: false,
                        labelString: "Catégories de produits"
                    }
                }]
            }
            */
        }
    });
}