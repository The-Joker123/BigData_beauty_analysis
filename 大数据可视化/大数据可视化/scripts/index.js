// var symptomName = last_month_day();

// $(function(){
//
//
//   // init();
//   // init2();
//     $("#el-dialog").addClass("hide");
//   $(".close").click(function(event) {
//     $("#el-dialog").addClass("hide");
//   });
//
//   var date = new Date();
//      var numble = date.getDate();
//      var today = getFormatMonth(new Date());
//      $("#date1").html(today);
//      $("#date2").html(today);
//      $("#date3").html(today);
//      $("#date4").html(today);
//
//
//   lay('.demo-input').each(function(){
//      laydate.render({
//         type: 'month',
//          elem: this,
//          trigger: 'click',
//          theme: '#95d7fb',
//          calendar: true,
//          showBottom: true,
//          done: function () {
//             console.log( $("#startDate").val())
//
//          }
//      })
//  });
//
// })
// function init(){
  //地图
  // var mapChart = echarts.init(document.getElementById('mapChart'));
  // mapChart.setOption({
  //     bmap: {
  //         center: [118.096435,24.485408],
  //         zoom: 12,
  //         roam: true,
  //
  //     },
  //     tooltip : {
  //         trigger: 'item',
  //         formatter:function(params, ticket, callback){
  //             return params.value[2]
  //         }
  //     },
  //     series: [{
  //         type: 'scatter',
  //         coordinateSystem: 'bmap',
  //         data: [
  //           [118.096435, 24.485408, ''] ,
  //           [118.094564, 24.457358, ''] ,
  //           [118.104103, 24.477761, ''],
  //           [118.14748, 24.506295, ''],
  //           [118.254841, 24.665349, ''],
  //          ]
  //     }]
  // });
  // mapChart.on('click', function (params) {
  //     $("#el-dialog").removeClass('hide');
  //     $("#reportTitle").html(params.value[2]);
  // });
  //
  // var bmap = mapChart.getModel().getComponent('bmap').getBMap()
  // bmap.addControl(new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_SATELLITE_MAP ]}));
  // bmap.setMapStyle({style:'midnight'})

//-----------------------------------------------
//各美妆品牌累计销售量
    fetch('http://localhost:8080/table/Top10_Sum_Brand', {
        method: "GET",
    }).then(function (response) {
        // 拿到响应数据并序列化成json
        return response.json();
    }).then(function (res) {

        var brand=[];
        for (i in res) {
            brand[i]= res[i].name;
        }

        var sum_sale_count=[];
        for (i in res) {
            sum_sale_count[i]= res[i];
        }

        console.log(brand);
        console.log(sum_sale_count);
        console.log(res[2]);


  var pieChart1 = echarts.init(document.getElementById('pieChart1'));
  pieChart1.setOption({


    color:["#87cefa","#ff7f50","#32cd32","#da70d6","#ea7ccc","#9a60b4","#fc8452","#3ba272","#73c0de","#ee6666"],

    legend: {
        y : '225',
        x : 'center',
        textStyle : {
            color : '#ffffff',

        },
         data : brand,
    },
    tooltip : {
        trigger: 'item',
        formatter: "{b},{c} 万 ({d}%)"
    },
    calculable : false,
    series : [
        {
            name:'累计销售量',
            type:'pie',
            radius : ['40%', '70%'],
            center : ['50%', '45%'],
            itemStyle : {
                normal : {
                    label : {
                        show : false
                    },
                    labelLine : {
                        show : false
                    }
                },
                emphasis : {
                    label : {
                        show : true,
                        position : 'center',
                        textStyle : {
                            fontSize : '20',
                            fontWeight : 'bold'
                        }
                    }
                }
            },
            data: sum_sale_count


        }
    ]
  })
    });

  //每日订单量走势
    //-----------------------------------------------------------------------------------------------------
     fetch('http://localhost:8080/table/dailySales', {
        method: "GET",
    }).then(function (response) {
        // 拿到响应数据并序列化成json
        return response.json();
    }).then(function (res) {
        console.log(res)
        console.log(Object.keys(res))
        // 基于准备好的dom，初始化echarts实例
   var lineChart1 = echarts.init(document.getElementById('Time_Analysis1'));
   lineChart1.setOption({
     title: {
        text: '每日整体销量走势',
        textStyle:{
           fontSize:16,
           color:'#ff7f50'
       },
    },
     color:["#ff7f50"],
     grid:{
         left: '20%',
         right: '1%',
         bottom: '20%',

     },
     tooltip : {
          trigger: 'item',
          formatter: ""
      },

     calculable : true,
         yAxis: [
             {
                 type: 'value',
                 axisLine:{
                     lineStyle:{
                         color: '#ff7f50'
                     },
                 },
                 splitLine: {
                     "show": false
                 },
                 axisLabel: {
                     textStyle: {
                         color: '#fff'
                     },
                     formatter: function (value) {
                         return value + ""
                     },
                 },
             }
         ],
         xAxis: [
             {
                 type: 'category',
                 data: Object.keys(res),
                 boundaryGap : false,
                 axisLine:{
                     lineStyle:{
                         color: '#ff7f50'
                     },
                 },
                 splitLine: {
                     "show": false
                 },
                 axisLabel: {
                   interval:0,
                   rotate:40,
                     textStyle: {
                         color: '#fff'
                     },
                     formatter: function (value) {
                         return value + ""
                     },
                 },
             }
         ],
     series : [
         {
             name:'订单量',
             type:'line',
             smooth:true,
             itemStyle: {normal: {areaStyle: {type: 'default'}}},
             data:Object.values(res)
         },
     ]

   })
    });

    //每月订购情况
    fetch('http://localhost:8080/sales_order_table/Monthly_subscription', {
        method: "GET",
    }).then(function (response) {
        // 拿到响应数据并序列化成json
        return response.json();
    }).then(function (res) {
        var  month=[];
        for (i in res) {
            month[i]= res[i].order_date+'月';

        }

        var  amounts=[];
        for (i in res) {
            amounts[i]= res[i].amount;

        }

        var  number_order=[];
        for (i in res) {
            number_order[i]= res[i].number_orders;

        }

        console.log(amounts);
    var histogramChart = echarts.init(document.getElementById('histogramChart'));
    histogramChart.setOption({
        color:["#87cefa","#ff7f50",],

        title: {
            subtext: '每月订购情况'
        },
        grid:{
            left: '5%',
            right: '3%',
            top:"23%",
            bottom: '7%',

        },
        tooltip: {
            trigger: 'axis',
            formatter: "{c}亿"
        },
        legend: {
            data: ['订购数量', '金额']
        },
        toolbox: {
            show: true,
            feature: {
                dataView: { show: true, readOnly: false }

            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                // prettier-ignore
                data: month,
                axisLine:{
                    lineStyle:{
                        color: '#ffffff'
                    },
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                name:'单位/亿',
                axisLine:{
                    lineStyle:{
                        color: '#ffffff'
                    },
                }
            }
        ],
        series: [
            {
                name: '订购数量',
                type: 'bar',
                data: number_order

            },
            {
                name: '金额',
                type: 'bar',
                data: amounts

            }
            ]
   })});


//哪里人最爱美
fetch('http://localhost:8080/sales_order_table/where_people_beauty', {
    method: "GET",
}).then(function (response) {
    // 拿到响应数据并序列化成json
    return response.json();
}).then(function (res) {
    var  location=[];
    for ( i in res) {
        location[i]= res[i].location;
    }
    location.reverse();

    var  amounts=[];
    for (i in res) {
        amounts[i]= res[i].amount;
    }
    amounts.reverse()

    console.log(location);
    console.log(amounts);
    var histogramChart = echarts.init(document.getElementById('lineChart2'));
    histogramChart.setOption({
        color:["#87cefa","#ff7f50",],

        title: {
            subtext: '每月订购情况'
        },
        grid:{
            left: '10%',
            right: '1%',
            top:"15%",
            bottom: '7%',

        },
        tooltip: {
            trigger: 'axis',
            formatter: "{b},{c}万"
        },
        legend: {
            data: ['订购数量']
        },
        toolbox: {
            show: true,
            feature: {
                dataView: { show: true, readOnly: false }
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'value',
                axisLine:{
                    lineStyle:{
                        color: '#ffffff'
                    },
                }
            }
        ],
        yAxis: [
            {
                type: 'category',
                data: location,
                axisLine:{
                    lineStyle:{
                        color: '#ffffff'
                    },
                }
            }
        ],
        series: [
            {
                name: '订购数量',
                type: 'bar',
                data: amounts
            }
        ]
    })});


//-----------------------------------------------------------------------------------------------------
//地区销售量
fetch('http://localhost:8080/sales_order_table/Regional_sales', {
    method: "GET",
}).then(function (response) {
    // 拿到响应数据并序列化成json
    return response.json();
}).then(function (res) {

    var data =[];
    for(i in res){
        data[i]=res[i];
    }
    console.log(data);
    var geoCoordMap = {
        海门: [121.15, 31.89],
        鄂尔多斯: [109.781327, 39.608266],
        招远: [120.38, 37.35],
        舟山: [122.207216, 29.985295],
        齐齐哈: [123.97, 47.33],
        盐城: [120.13, 33.38],
        赤峰: [118.87, 42.28],
        青岛: [120.33, 36.07],
        乳山: [121.52, 36.89],
        金昌: [102.188043, 38.520089],
        泉州: [118.58, 24.93],
        莱西: [120.53, 36.86],
        日照: [119.46, 35.42],
        胶南: [119.97, 35.88],
        南通: [121.05, 32.08],
        拉萨: [91.11, 29.97],
        云浮: [112.02, 22.93],
        梅州: [116.1, 24.55],
        文登: [122.05, 37.2],
        上海: [121.48, 31.22],
        攀枝花: [101.718637, 26.582347],
        威海: [122.1, 37.5],
        承德: [117.93, 40.97],
        厦门: [118.1, 24.46],
        汕尾: [115.375279, 22.786211],
        潮州: [116.63, 23.68],
        丹东: [124.37, 40.13],
        太仓: [121.1, 31.45],
        曲靖: [103.79, 25.51],
        烟台: [121.39, 37.52],
        福州: [119.3, 26.08],
        瓦房店: [121.979603, 39.627114],
        即墨: [120.45, 36.38],
        抚顺: [123.97, 41.97],
        玉溪: [102.52, 24.35],
        张家口: [114.87, 40.82],
        阳泉: [113.57, 37.85],
        莱州: [119.942327, 37.177017],
        湖州: [120.1, 30.86],
        汕头: [116.69, 23.39],
        昆山: [120.95, 31.39],
        宁波: [121.56, 29.86],
        湛江: [110.359377, 21.270708],
        揭阳: [116.35, 23.55],
        荣成: [122.41, 37.16],
        连云港: [119.16, 34.59],
        葫芦岛: [120.836932, 40.711052],
        常熟: [120.74, 31.64],
        东莞: [113.75, 23.04],
        河源: [114.68, 23.73],
        淮安: [119.15, 33.5],
        泰州: [119.9, 32.49],
        南宁: [108.33, 22.84],
        营口: [122.18, 40.65],
        惠州: [114.4, 23.09],
        江阴: [120.26, 31.91],
        蓬莱: [120.75, 37.8],
        韶关: [113.62, 24.84],
        嘉峪关: [98.289152, 39.77313],
        广州: [113.23, 23.16],
        延安: [109.47, 36.6],
        太原: [112.53, 37.87],
        清远: [113.01, 23.7],
        中山: [113.38, 22.52],
        昆明: [102.73, 25.04],
        寿光: [118.73, 36.86],
        盘锦: [122.070714, 41.119997],
        长治: [113.08, 36.18],
        深圳: [114.07, 22.62],
        珠海: [113.52, 22.3],
        宿迁: [118.3, 33.96],
        咸阳: [108.72, 34.36],
        铜川: [109.11, 35.09],
        平度: [119.97, 36.77],
        佛山: [113.11, 23.05],
        海口: [110.35, 20.02],
        江门: [113.06, 22.61],
        章丘: [117.53, 36.72],
        肇庆: [112.44, 23.05],
        大连: [121.62, 38.92],
        临汾: [111.5, 36.08],
        吴江: [120.63, 31.16],
        石嘴山: [106.39, 39.04],
        沈阳: [123.38, 41.8],
        苏州: [120.62, 31.32],
        茂名: [110.88, 21.68],
        嘉兴: [120.76, 30.77],
        长春: [125.35, 43.88],
        胶州: [120.03336, 36.264622],
        银川: [106.27, 38.47],
        张家港: [120.555821, 31.875428],
        三门峡: [111.19, 34.76],
        锦州: [121.15, 41.13],
        南昌: [115.89, 28.68],
        柳州: [109.4, 24.33],
        三亚: [109.511909, 18.252847],
        自贡: [104.778442, 29.33903],
        吉林: [126.57, 43.87],
        阳江: [111.95, 21.85],
        泸州: [105.39, 28.91],
        西宁: [101.74, 36.56],
        宜宾: [104.56, 29.77],
        呼和浩特: [111.65, 40.82],
        成都: [104.06, 30.67],
        大同: [113.3, 40.12],
        镇江: [119.44, 32.2],
        桂林: [110.28, 25.29],
        张家界: [110.479191, 29.117096],
        宜兴: [119.82, 31.36],
        北海: [109.12, 21.49],
        西安: [108.95, 34.27],
        金坛: [119.56, 31.74],
        东营: [118.49, 37.46],
        牡丹江: [129.58, 44.6],
        遵义: [106.9, 27.7],
        绍兴: [120.58, 30.01],
        扬州: [119.42, 32.39],
        常州: [119.95, 31.79],
        潍坊: [119.1, 36.62],
        重庆: [106.54, 29.59],
        台州: [121.420757, 28.656386],
        南京: [118.78, 32.04],
        滨州: [118.03, 37.36],
        贵阳: [106.71, 26.57],
        无锡: [120.29, 31.59],
        本溪: [123.73, 41.3],
        克拉玛依: [84.77, 45.59],
        渭南: [109.5, 34.52],
        马鞍山: [118.48, 31.56],
        宝鸡: [107.15, 34.38],
        焦作: [113.21, 35.24],
        句容: [119.16, 31.95],
        北京: [116.46, 39.92],
        徐州: [117.2, 34.26],
        衡水: [115.72, 37.72],
        包头: [110, 40.58],
        绵阳: [104.73, 31.48],
        乌鲁木齐: [87.68, 43.77],
        枣庄: [117.57, 34.86],
        杭州: [120.19, 30.26],
        淄博: [118.05, 36.78],
        鞍山: [122.85, 41.12],
        溧阳: [119.48, 31.43],
        库尔勒: [86.06, 41.68],
        安阳: [114.35, 36.1],
        开封: [114.35, 34.79],
        济南: [117, 36.65],
        德阳: [104.37, 31.13],
        温州: [120.65, 28.01],
        九江: [115.97, 29.71],
        邯郸: [114.47, 36.6],
        临安: [119.72, 30.23],
        兰州: [103.73, 36.03],
        沧州: [116.83, 38.33],
        临沂: [118.35, 35.05],
        南充: [106.110698, 30.837793],
        天津: [117.2, 39.13],
        富阳: [119.95, 30.07],
        泰安: [117.13, 36.18],
        诸暨: [120.23, 29.71],
        郑州: [113.65, 34.76],
        哈滨: [126.63, 45.75],
        聊城: [115.97, 36.45],
        芜湖: [118.38, 31.33],
        唐山: [118.02, 39.63],
        平顶山: [113.29, 33.75],
        邢台: [114.48, 37.05],
        德州: [116.29, 37.45],
        济宁: [116.59, 35.38],
        荆州: [112.239741, 30.335165],
        宜昌: [111.3, 30.7],
        义乌: [120.06, 29.32],
        丽水: [119.92, 28.45],
        洛阳: [112.44, 34.7],
        秦皇岛: [119.57, 39.95],
        株洲: [113.16, 27.83],
        石家庄: [114.48, 38.03],
        莱芜: [117.67, 36.19],
        常德: [111.69, 29.05],
        保定: [115.48, 38.85],
        湘潭: [112.91, 27.87],
        金华: [119.64, 29.12],
        岳阳: [113.09, 29.37],
        长沙: [113, 28.21],
        衢州: [118.88, 28.97],
        廊坊: [116.7, 39.53],
        菏泽: [115.480656, 35.23375],
        合肥: [117.27, 31.86],
        武汉: [114.31, 30.52],
        大庆: [125.03, 46.58]
    };

    //转换新的地理坐标
    var convertData = function (data) {
        var res = [];
        for (var i = 0; i < data.length; i++) {
            // geoCoordMap:地理坐标图
            var geoCoord = geoCoordMap[data[i].name];
            //匹配新的地理坐标
            if (geoCoord) {
                res.push({
                    name: data[i].name,
                    value: geoCoord.concat(data[i].value)
                });
            }
        }
        return res;
    };


    var chartDom = document.getElementById('map');
    var myChart = echarts.init(chartDom);

    var option;

    option = {
        backgroundColor: 'transparent',
        title: {
            text: '全国美妆销售数据量',
            subtext: 'data',
            sublink: '',
            left: 'center',
            textStyle: {
                color: '#fff'
            }
        },
        tooltip: {
            trigger: 'item'
        },
        bmap: {
            center: [114.31, 30.52],
            zoom: 6,
            roam: true,
            mapStyle: {
                styleJson: [
                    {
                        featureType: 'water',
                        elementType: 'all',
                        stylers: {
                            color: '#044161'
                        }
                    },
                    {
                        featureType: 'land',
                        elementType: 'all',
                        stylers: {
                            color: '#004981'
                        }
                    },
                    {
                        featureType: 'boundary',
                        elementType: 'geometry',
                        stylers: {
                            color: '#064f85'
                        }
                    },
                    {
                        featureType: 'railway',
                        elementType: 'all',
                        stylers: {
                            visibility: 'off'
                        }
                    },
                    {
                        featureType: 'highway',
                        elementType: 'geometry',
                        stylers: {
                            color: '#004981'
                        }
                    },
                    {
                        featureType: 'highway',
                        elementType: 'geometry.fill',
                        stylers: {
                            color: '#005b96',
                            lightness: 1
                        }
                    },
                    {
                        featureType: 'highway',
                        elementType: 'labels',
                        stylers: {
                            visibility: 'off'
                        }
                    },
                    {
                        featureType: 'arterial',
                        elementType: 'geometry',
                        stylers: {
                            color: '#004981'
                        }
                    },
                    {
                        featureType: 'arterial',
                        elementType: 'geometry.fill',
                        stylers: {
                            color: '#00508b'
                        }
                    },
                    {
                        featureType: 'poi',
                        elementType: 'all',
                        stylers: {
                            visibility: 'off'
                        }
                    },
                    {
                        featureType: 'green',
                        elementType: 'all',
                        stylers: {
                            color: '#056197',
                            visibility: 'off'
                        }
                    },
                    {
                        featureType: 'subway',
                        elementType: 'all',
                        stylers: {
                            visibility: 'off'
                        }
                    },
                    {
                        featureType: 'manmade',
                        elementType: 'all',
                        stylers: {
                            visibility: 'off'
                        }
                    },
                    {
                        featureType: 'local',
                        elementType: 'all',
                        stylers: {
                            visibility: 'off'
                        }
                    },
                    {
                        featureType: 'arterial',
                        elementType: 'labels',
                        stylers: {
                            visibility: 'off'
                        }
                    },
                    {
                        featureType: 'boundary',
                        elementType: 'geometry.fill',
                        stylers: {
                            color: '#029fd4'
                        }
                    },
                    {
                        featureType: 'building',
                        elementType: 'all',
                        stylers: {
                            color: '#1a5787'
                        }
                    },
                    {
                        featureType: 'label',
                        elementType: 'all',
                        stylers: {
                            visibility: 'off'
                        }
                    }
                ]
            }
        },
        series: [
            {
                name: '销售量',
                type: 'scatter',
                coordinateSystem: 'bmap',
                data: convertData(data),
                encode: {
                    value: 2
                },
                symbolSize: function (val) {
                    console.log("-----"+val[2]);
                    return val[2]/3;
                },
                label: {
                    formatter: '{b}',
                    position: 'right'
                },
                // tooltip : {
                //     formatter: "{c}万"
                // },
                itemStyle: {
                    color: '#ddb926'
                },
                emphasis: {
                    label: {
                        show: true
                    }
                }
            }
            ,
            {
                            name: 'Top 5',
                            type: 'effectScatter',
                            coordinateSystem: 'bmap',
                            data: convertData(
                                data
                                    .sort(function (a, b) {
                                        return b.value - a.value;
                                    })
                                    .slice(0, 6)
                            ),
                            encode: {
                                value: 2
                            },
                            symbolSize: function (val) {
                                return val[2] / 10;
                            },
                            showEffectOn: 'emphasis',
                            rippleEffect: {
                                brushType: 'stroke'
                            },
                            hoverAnimation: true,
                            label: {
                                formatter: '{b}',
                                position: 'right',
                                show: true
                            },
                            itemStyle: {
                                color: '#f4e925',
                                shadowBlur: 10,
                                shadowColor: '#333'
                            },
                            zlevel: 1
            }
            // {
            //     type: 'custom',
            //     coordinateSystem: 'bmap',
            //     renderItem: renderItem,
            //     itemStyle: {
            //         opacity: 0.5
            //     },
            //     animation: false,
            //     silent: true,
            //     data: [0],
            //     z: -10
            // }
        ]
    };

    option && myChart.setOption(option);

});

    //-----------------------------------------------------------------------------------------------------
    //前10美妆销售量排名
     fetch('http://localhost:8080/table/Top10_Day_Brand', {
        method: "GET",
    }).then(function (response) {
        // 拿到响应数据并序列化成json
        return response.json();
    }).then(function (res) {


         var sale_count_20161105=[], sale_amount_20161105=[];
         for (i in res) {
             sale_count_20161105[i]= res[0][i].sale_count/10000;
             console.log(sale_count_20161105[i]+" /"+i);
             sale_amount_20161105[i]= res[0][i].sale_amount/10000/100;
         }

         var sale_count_20161106=[], sale_amount_20161106=[];
         for (i in res) {
             sale_count_20161106[i]= res[1][i].sale_count/10000;
             sale_amount_20161106[i]= res[1][i].sale_amount/10000/100;
         }

         var sale_count_20161107=[], sale_amount_20161107=[];
         for (i in res) {
             sale_count_20161107[i]= res[2][i].sale_count/10000;
             sale_amount_20161107[i]=res[2][i].sale_amount/10000/100;
         }

         var sale_count_20161108=[], sale_amount_20161108=[];
         for (i in res) {
             sale_count_20161108[i]= res[3][i].sale_count/10000;
             sale_amount_20161108[i]= res[3][i].sale_amount/10000/100;
         }

         var sale_count_20161109=[], sale_amount_20161109=[];
         for (i in res) {
             sale_count_20161109[i]= res[4][i].sale_count/10000;
             sale_amount_20161109[i]= res[4][i].sale_amount/10000/100;
         }

         var sale_count_20161110=[], sale_amount_20161110=[];
         for (i in res) {
             sale_count_20161110[i]= res[5][i].sale_count/10000;
             sale_amount_20161110[i]= res[5][i].sale_amount/10000/100;
         }

         var sale_count_20161111=[], sale_amount_20161111=[];
         for (i in res) {
             sale_count_20161111[i]= res[6][i].sale_count/10000;
             sale_amount_20161111[i]= res[6][i].sale_amount/10000/100;
         }

         var sale_count_20161112=[], sale_amount_20161112=[];
         for (i in res) {
             sale_count_20161112[i]= res[7][i].sale_count/10000;
             sale_amount_20161112[i]= res[7][i].sale_amount/10000/100;
         }

         var sale_count_20161113=[], sale_amount_20161113=[];
         for (i in res) {
             sale_count_20161113[i]= res[8][i].sale_count/10000;
             sale_amount_20161113[i]= res[8][i].sale_amount/10000/100;
         }

         var sale_count_20161114=[], sale_amount_20161114=[];
         for (i in res) {
             sale_count_20161114[i]= res[9][i].sale_count/10000;
             sale_amount_20161114[i]= res[9][i].sale_amount/10000/100;
         }

         var brand_20161105=[];
         for (i in res) {
             brand_20161105[i]= res[0][i].brand;

         }
         var years=[];
         for (i in res) {
             years[i]= res[i][0].year;

         }

         console.log(years);


         var chartDom = document.getElementById('count_amount');
         var myChart = echarts.init(chartDom);

         var option;

         option = {
             baseOption: {
                 timeline: {

                     axisType: 'category',
                     // realtime: false,
                     // loop: false,
                     autoPlay: true,
                     // currentIndex: 2,
                     playInterval: 1000,
                     // controlStyle: {
                     //     position: 'left'
                     // },
                     data:years,
                     label: {
                         color: "rgba(255, 255, 255, 1)"
                     }
                 },
                 title: {
                     subtext: '',
                     textStyle: {

                         color: "rgba(255, 255, 255, 1)"
                     },
                 },
                 tooltip: {},
                 legend: {
                     textStyle: {
                         color: "rgba(255, 255, 255, 255)"
                     },
                     left: 267,
                     top:10,
                     data: ['销售额', '销售量']
                 },
                 calculable: true,
                 grid: {
                     top: 80,
                     bottom: 100,
                     // width:400,
                     tooltip: {
                         trigger: 'axis',
                         axisPointer: {
                             type: 'shadow',
                             label: {
                                 show: true,
                                 formatter: function (params) {
                                     return params.value.replace('\n', '');
                                 }
                             }
                         }
                     }
                 },
                 xAxis: [
                     {
                         type: 'category',

                         axisLabel: { interval: 0 },
                         data:brand_20161105,
                         axisLine:{
                             lineStyle:{
                                 color: '#ffffff'
                             },
                         },
                         splitLine: { show: false }
                     },

                 ],
                 series1: [

                 ],
                 yAxis: [
                     {
                         type:'value',
                         axisLine:{
                             lineStyle:{
                                 color: '#ffffff'
                             },
                         },
                         name: 'GDP（亿元）'

                     }
                 ],
                 series: [
                     { name: '销售量', type: 'bar' },
                     { name: '销售额', type: 'bar' }

                 ]
             },
             options: [
                 {
                     title: { text: '2016-11-05各美妆销售量' },
                     series1: [
                         { data: brand_20161105},

                     ],
                     series: [
                         { data: sale_count_20161105},
                         { data: sale_amount_20161105},

                     ]

                 },
                 {
                     title: { text: '2016-11-06各美妆销售量' },
                     series: [
                         { data: Object.values(sale_count_20161106)},
                         { data: sale_amount_20161106},

                     ]
                 },
                 {
                     title: { text: '2016-11-07各美妆销售量' },
                     series: [
                         { data: Object.values(sale_count_20161107)},
                         { data: Object.values(sale_amount_20161107)},

                     ]
                 },
                 {
                     title: { text: '2016-11-08各美妆销售量' },
                     series: [
                         { data: Object.values(sale_count_20161108)},
                         { data: Object.values(sale_amount_20161108)},
                     ]
                 },
                 {
                     title: { text: '2016-11-09各美妆销售量' },
                     series: [
                         { data: Object.values(sale_count_20161109)},
                         { data: Object.values(sale_amount_20161109)},
                     ]
                 },
                 {
                     title: { text: '2016-11-10各美妆销售量' },
                     series: [
                         { data: Object.values(sale_count_20161110)},
                         { data: Object.values(sale_amount_20161110)},

                     ]
                 },
                 {
                     title: { text: '2016-11-11各美妆销售量' },
                     series: [
                         { data: Object.values(sale_count_20161111)},
                         { data: Object.values(sale_amount_20161111)},

                     ]
                 },
                 {
                     title: { text: '2016-11-12各美妆销售量' },

                     series: [
                         { data: Object.values(sale_count_20161112)},
                         { data: Object.values(sale_amount_20161112)},

                     ]
                 },
                 {
                     title: { text: '2016-11-13各美妆销售量'},
                     series: [
                         { data: Object.values(sale_count_20161113)},
                         { data: Object.values(sale_amount_20161113)},

                     ]
                 },
                 {
                     title: { text: '2016-11-14各美妆销售量' },
                     series: [
                         { data: Object.values(sale_count_20161114)},
                         { data: Object.values(sale_amount_20161114)},

                     ]
                 }
             ]
         };

         option && myChart.setOption(option);

    });
