/**
 * 創建圓餅圖Html，藉由控制項、問題名稱、問題回答陣列和使用者回答編號陣列。
 * @param Stirng 控制項
 * @param String 問題名稱
 * @param Array 問題回答陣列
 * @param Array 使用者回答編號陣列
 */
function CreatePieChart(strSelector, strQuestionName, arrQuestionAnswer, arrEachUserAnswerNum) {
    function setTranslation(p, slice) {
        p.sliced = slice;
        if (p.sliced) {
            p.graphic.animate(p.slicedTranslation);
        }
        else {
            p.graphic.animate({
                translateX: 0,
                translateY: 0
            });
        }
    }

    var options = {
        chart: {
            renderTo: strSelector,
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: null
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b> ({point.y})'
        },
        plotOptions: {
            pie: {
                point: {
                    events: {
                        mouseOut: function () {
                            setTranslation(this, false);
                        },
                        mouseOver: function () {
                            setTranslation(this, true);
                        }
                    }
                },
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f}% ({point.y})',
                    style: {
                        fontWeight: 'bold',
                        fontSize: '1rem',
                    }
                }
            }
        },
        series: [],
        credits: {
            enabled: false
        }
    }

    var seriesOptions = {
        name: strQuestionName,
        data: [],
        colorByPoint: true,
    }

    for (i = 0; i < arrQuestionAnswer.length; i++) {
        var dataOptions = {
            name: '',
            y: ''
        }
        dataOptions.name = arrQuestionAnswer[i];
        dataOptions.y = arrEachUserAnswerNum[i] == null ? 0 : arrEachUserAnswerNum[i];
        seriesOptions.data.push(dataOptions);
    }

    options.series.push(seriesOptions);
    var chart = new Highcharts.Chart(options);
}
/**
 * 創建問卷統計Html，藉由問題陣列和使用者回答陣列。
 * @param Array 問題陣列
 * @param Array 使用者回答陣列
 */
function CreateQuestionnaireStatistics(objArrQuestion, objArrUserAnswer) {
    $(divQuestionnaireStatisticsContainer).append(
        `
            <div class="row align-items-center justify-content-center">
                <div class="col-md-10">
                    <div id="divQuestionnaireStatisticsInnerContainer"
                         class="row align-items-center justify-content-center gy-3 gy-md-5"
                    >
                    </div>
                </div>
            </div>
        `
    );

    let i = 1;
    for (let question of objArrQuestion) {
        let questionId = question.questionId;
        let questionName = question.questionName;
        let questionRequired = question.questionRequired;
        let questionTyping = question.questionTyping;
        let arrQuestionAnswer = question.questionAnswer.split(";");

        let arrQuestionItsUserAnswer = 
        	(!objArrUserAnswer || !objArrUserAnswer.length) 
	        	? null 
	        	: objArrUserAnswer.filter(item => item.questionId === questionId);

        let arrUserAnswerNum = [];
        if (arrQuestionItsUserAnswer && arrQuestionItsUserAnswer.length)
            arrUserAnswerNum = arrQuestionItsUserAnswer.map(item2 => item2.answerNum - 1);
        else
            arrUserAnswerNum.push(-1);

        let arrEachUserAnswerNum = [];
        if (arrUserAnswerNum.indexOf(-1) === -1) {
            arrEachUserAnswerNum = arrUserAnswerNum.reduce((acc, val) => {
                acc[val] = acc[val] == null ? 1 : acc[val] += 1;
                return acc;
            }, []);
        }
        else
            arrEachUserAnswerNum = new Array(arrQuestionAnswer.length).fill(null);

        let resultQuestionName = `${i}. ${questionName} ${questionRequired ? "(必填)" : ""}`;

        if (!arrQuestionItsUserAnswer || !arrQuestionItsUserAnswer.length) {
            $("#divQuestionnaireStatisticsInnerContainer").append(
                `
                    <div class="col-12">
                        <div class="d-flex flex-column">
                            <h2>
                                ${resultQuestionName}
                            </h2>
                            <p>
                                ${emptyMessageOfUserListOrStatistics}
                            </p>
                        </div>
                    </div>
                `
            );
        }
        else if (questionTyping === TEXT) {
            $("#divQuestionnaireStatisticsInnerContainer").append(
                `
                    <div class="col-12">
                        <div class="d-flex flex-column">
                            <h2>
                                ${resultQuestionName}
                            </h2>
                            <p>
                                -
                            </p>
                        </div>
                    </div>
                `
            );
        }
        else {
            let dynamicQuestionnaireStatistics = `divQuestionnaireStatistics_${questionId}`;

            $("#divQuestionnaireStatisticsInnerContainer").append(
                `
                    <div class="col-12">
                        <div class="d-flex flex-column">
                            <h2>
                                ${resultQuestionName}
                            </h2>
                            <div id="${dynamicQuestionnaireStatistics}"></div>
                        </div>
                    </div>
                `
            );

            CreatePieChart(
                dynamicQuestionnaireStatistics,
                questionName,
                arrQuestionAnswer,
                arrEachUserAnswerNum
            );
        }

        i++;
    }
}
/**
 * 取得問卷統計回應Body，藉由問卷Id。
 * @param String 問卷Id
 */
function GetQuestionnaireStatistics(strQuestionnaireId) {
	let objPostData = { [DataProperty.QUESTIONNAIRE_ID.key]: strQuestionnaireId };
	
    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/getQuestionnaireStatistics`,
        method: METHOD_POST,
		contentType: APPLICATION_JSON,
		dataType: DATA_TYPE_JSON,
		data: JSON.stringify(objPostData),
        success: function (objQuestionnaireStatisticsResp) {
			let { status_code, 
				message, 
				question_list, 
				user_answer_list } = objQuestionnaireStatisticsResp;
				
            if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
            }
            else {
                CreateQuestionnaireStatistics(question_list, user_answer_list);
            }
        },
        error: function (msg) {
            console.log(msg);
            alert(errorMessageOfAjax);
        }
    });
}