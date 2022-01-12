var symptomName = last_month_day();

$(function(){


  init();
  init2();
    $("#el-dialog").addClass("hide");
  $(".close").click(function(event) {
    $("#el-dialog").addClass("hide");
  });

  var date = new Date();
     var numble = date.getDate();
     var today = getFormatMonth(new Date());
     $("#date1").html(today);
     $("#date2").html(today);
     $("#date3").html(today);
     $("#date4").html(today);


  lay('.demo-input').each(function(){
     laydate.render({
        type: 'month',
         elem: this,
         trigger: 'click',
         theme: '#95d7fb',
         calendar: true,
         showBottom: true,
         done: function () {
            console.log( $("#startDate").val())

         }
     })
 });

})
function init(){
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
  //           [118.096435, 24.485408, '厦门市'] ,
  //           [118.094564, 24.457358, '厦门第一医院'] ,
  //           [118.104103, 24.477761, '厦门中山医院'],
  //           [118.14748, 24.506295, '厦门中医院'],
  //           [118.254841, 24.665349, '厦门第五医院'],
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
    fetch('http://localhost:8081/table/Top10_Sum_Brand', {
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
        formatter: "{a}<br/>{b}<br/>{c} ({d}%)"
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
     fetch('http://localhost:8081/table/dailySales', {
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

    //-----------------------------------------------------------------------------------------------------

    var histogramChart = echarts.init(document.getElementById('histogramChart'));
    histogramChart.setOption({

      color:["#87cefa","#ff7f50","#32cd32","#da70d6",],
      legend: {
          y : '250',
          x : 'center',
          data:['厦门第一医院', '厦门中山医院','厦门中医院','厦门第五医院'],
          textStyle : {
              color : '#ffffff',

          }
      },

      calculable :false,


      grid:{
              left: '5%',
              right: '5%',
              bottom: '20%',
              containLabel: true
      },

      tooltip : {
          trigger: 'axis',
          axisPointer : {
              type : 'shadow'
          }
      },

      xAxis : [
          {
              type : 'value',
              axisLabel: {
                  show: true,
                  textStyle: {
                      color: '#fff'
                  }
              },
              splitLine:{
                  lineStyle:{
                      color:['#f2f2f2'],
                      width:0,
                      type:'solid'
                  }
              }

          }
      ],

      yAxis : [
          {
              type : 'category',
              data:['门诊人数(人)', '住院人次(人)','人均费用(元)'],
              axisLabel: {
                  show: true,
                  textStyle: {
                      color: '#fff'
                  }
              },
              splitLine:{
                  lineStyle:{
                      width:0,
                      type:'solid'
                  }
              }
          }
      ],

      series : [
          {
              name:'厦门第一医院',
              type:'bar',
              stack: '总量',
              itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
              data:[320, 302, 301]
          },
          {
              name:'厦门中山医院',
              type:'bar',
              stack: '总量',
              itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
              data:[120, 132, 101]
          },
          {
              name:'厦门中医院',
              type:'bar',
              stack: '总量',
              itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
              data:[220, 182, 191]
          },
          {
              name:'厦门第五医院',
              type:'bar',
              stack: '总量',
              itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
              data:[150, 212, 201]
          }

      ]
   });

   var lineChart2 = echarts.init(document.getElementById('lineChart2'));
   lineChart2.setOption({

     color:["#87cefa","#ff7f50","#32cd32","#da70d6",],
     legend: {
         y : '260',
         x : 'center',
         textStyle : {
             color : '#ffffff',

         },
          data : ['厦门第一医院','厦门中山医院','厦门中医院','厦门第五医院',],
     },
     calculable : false,
     tooltip : {
         trigger: 'item',
         formatter: "{a}<br/>{b}<br/>{c}条"
     },
     yAxis: [
           {
               type: 'value',
               axisLine : {onZero: false},
               axisLine:{
                   lineStyle:{
                       color: '#034c6a'
                   },
               },

               axisLabel: {
                   textStyle: {
                       color: '#fff'
                   },
                   formatter: function (value) {
                       return value + "k条"
                   },
               },
               splitLine:{
                   lineStyle:{
                       width:0,
                       type:'solid'
                   }
               }
           }
       ],
       xAxis: [
           {
               type: 'category',
               data : ['8:00','10:00','12:00','14:00','16:00','18:00'],
               axisLine:{
                   lineStyle:{
                       color: '#034c6a'
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
               splitLine:{
                   lineStyle:{
                       width:0,
                       type:'solid'
                   }
               },
           }
       ],
       grid:{
               left: '5%',
               right: '5%',
               bottom: '20%',
               containLabel: true
       },
       series : [
         {
             name:'厦门第一医院',
             type:'line',
             smooth:true,
             itemStyle: {
                 normal: {
                     lineStyle: {
                         shadowColor : 'rgba(0,0,0,0.4)'
                     }
                 }
             },
             data:[15, 0, 20, 45, 22.1, 25,].reverse()
         },
         {
             name:'厦门中山医院',
             type:'line',
             smooth:true,
             itemStyle: {
                 normal: {
                     lineStyle: {
                         shadowColor : 'rgba(0,0,0,0.4)'
                     }
                 }
             },
             data:[25, 10, 30, 55, 32.1, 35, ].reverse()
         },
         {
             name:'厦门中医院',
             type:'line',
             smooth:true,
             itemStyle: {
                 normal: {
                     lineStyle: {
                         shadowColor : 'rgba(0,0,0,0.4)'
                     }
                 }
             },
             data:[35, 20, 40, 65, 42.1, 45, ].reverse()
         },
         {
             name:'厦门第五医院',
             type:'line',
             smooth:true,
             itemStyle: {
                 normal: {
                     lineStyle: {
                         shadowColor : 'rgba(0,0,0,0.4)'
                     }
                 }
             },
             data:[45, 30, 50, 75, 52.1, 55, 6].reverse()
         }
     ]
   });



}

function init2(){
  var lineChart3 = echarts.init(document.getElementById('lineChart3'));
  lineChart3.setOption({

    color:["#87cefa","#ff7f50",],
    legend: {
        y : 'top',
        x : 'center',
        textStyle : {
            color : '#ffffff',

        },
         data : ['门诊人次','住院人次'],
    },
    calculable : false,
    tooltip : {
        trigger: 'item',
        formatter: "{a}<br/>{b}<br/>{c}人"
    },
    dataZoom: {
         show: true,
         realtime : true,
         start: 0,
         end: 18,
         height: 20,
         backgroundColor: '#f8f8f8',
         dataBackgroundColor: '#e4e4e4',
         fillerColor: '#87cefa',
         handleColor: '#87cefa',
     },
    yAxis: [
          {
              type: 'value',
              axisLine : {onZero: false},
              axisLine:{
                  lineStyle:{
                      color: '#034c6a'
                  },
              },

              axisLabel: {
                  textStyle: {
                      color: '#fff'
                  },
                  formatter: function (value) {
                      return value + "人"
                  },
              },
              splitLine:{
                  lineStyle:{
                      width:0,
                      type:'solid'
                  }
              }
          }
      ],
      xAxis: [
          {
              type: 'category',
              data : symptomName,
              boundaryGap : false,
              axisLine:{
                  lineStyle:{
                      color: '#034c6a'
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
              splitLine:{
                  lineStyle:{
                      width:0,
                      type:'solid'
                  }
              },
          }
      ],
      grid:{
              left: '5%',
              right: '5%',
              bottom: '20%',
              containLabel: true
      },
      series : [
        {
            name:'门诊费用',
            type:'line',
            smooth:true,
            itemStyle: {
                normal: {
                    lineStyle: {
                        shadowColor : 'rgba(0,0,0,0.4)'
                    }
                }
            },
            data:[1150, 180, 2100, 2415, 1212.1, 3125,1510, 810, 2100, 2415, 1122.1, 3215,1510, 801, 2001, 2245, 1232.1, 3245,1520, 830, 2200, 2145, 1223.1, 3225,150, 80, 200, 245, 122.1, 325]
        },
        {
            name:'住院费用',
            type:'line',
            smooth:true,
            itemStyle: {
                normal: {
                    lineStyle: {
                        shadowColor : 'rgba(0,0,0,0.4)'
                    }
                }
            },
            data:[2500, 1000, 3000, 5005, 3200.1, 3005, 2500, 1000, 3000, 5005, 3200.1, 3005,2500, 1000, 3000, 5005, 3200.1, 3005,2500, 1000, 3000, 5005, 3200.1, 3005, 2500, 1000, 3000, 5005, 3200.1, 3005,2500, 1000, 3000, 5005, 3200.1, 3005,]
        },
    ]
  });


  var lineChart4 = echarts.init(document.getElementById('lineChart4'));
  lineChart4.setOption({

    color:["#87cefa","#ff7f50",],
    calculable : false,
    tooltip : {
        trigger: 'item',
        formatter: "{a}<br/>{b}<br/>{c}元"
    },
    dataZoom: {
         show: true,
         realtime : true,
         start: 0,
         end: 18,
         height: 20,
         backgroundColor: '#f8f8f8',
         dataBackgroundColor: '#e4e4e4',
         fillerColor: '#87cefa',
         handleColor: '#87cefa',
     },
    yAxis: [
          {
              type: 'value',
              axisLine : {onZero: false},
              axisLine:{
                  lineStyle:{
                      color: '#034c6a'
                  },
              },

              axisLabel: {
                  textStyle: {
                      color: '#fff'
                  },
                  formatter: function (value) {
                      return value + "元"
                  },
              },
              splitLine:{
                  lineStyle:{
                      width:0,
                      type:'solid'
                  }
              }
          }
      ],
      xAxis: [
          {
              type: 'category',
              data : symptomName,
              boundaryGap : false,
              axisLine:{
                  lineStyle:{
                      color: '#034c6a'
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
              splitLine:{
                  lineStyle:{
                      width:0,
                      type:'solid'
                  }
              },
          }
      ],
      grid:{
              left: '5%',
              right: '5%',
              bottom: '20%',
              containLabel: true
      },
      series : [
        {
            name:'医疗费用',
            type:'line',
            smooth:true,
            itemStyle: {
                normal: {
                    lineStyle: {
                        shadowColor : 'rgba(0,0,0,0.4)'
                    }
                }
            },
            data:[1500, 800, 1200, 2450, 1122.1, 1325,1150, 180, 1200, 1245, 1122.1, 1325,150, 180, 1200, 2145, 1212.1, 3215,1510, 180, 2100, 2415, 122.1, 325,150, 80, 200, 245, 122.1, 325].reverse()
        },
    ]
  });

  //年龄分布
  var pieChart2 = echarts.init(document.getElementById('pieChart2'));
  pieChart2.setOption({
    color:["#32cd32","#ff7f50","#87cefa","#FD6C88","#4b5cc4","#faff72"],
    tooltip : {
     trigger: 'item',
     formatter: "{a}<br/>{b}<br/>{c}人"
    },
    calculable : true,
    series : [
        {
            name:'发病人数',
            type:'pie',
            radius : [30, 110],
            center : ['50%', '50%'],
            roseType : 'area',
            x: '50%',



            sort : 'ascending',
            data:[
                {value:10, name:'婴儿(1-3岁)'},
                {value:5, name:'少儿(4-10岁)'},
                {value:15, name:'少年(10-18岁)'},
                {value:25, name:'青年(18-45岁)'},
                {value:125, name:'中年(45-60岁)'},
                {value:175, name:'老年(60岁以上)'},
            ]
        }
    ]
  })

  //医疗费用组成
  var pieChart3 = echarts.init(document.getElementById('pieChart3'));
  pieChart3.setOption({
    color:["#32cd32","#ff7f50","#87cefa","#FD6C88","#4b5cc4","#faff72"],
    tooltip : {
     trigger: 'item',
     formatter: "{a}<br/>{b}<br/>{c}元"
    },
    calculable : true,
    series : [
        {
            name:'发病人数',
            type:'pie',
            radius : [30, 110],
            center : ['50%', '50%'],
            roseType : 'area',
            x: '50%',



            sort : 'ascending',
            data:[
                {value:10, name:'诊察费用'},
                {value:500, name:'检查费用'},
                {value:150, name:'检验费用'},
                {value:250, name:'西药费用'},
                {value:125, name:'中药费用'},
                {value:1750, name:'手术费用'},
            ]
        }
    ]
  })
    //-----------------------------------------------------------------------------------------------------
    //各美妆销售量
     fetch('http://localhost:8081/table/Top20_Day_Brand', {
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


         var chartDom = document.getElementById('count_amount')
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






     })};
