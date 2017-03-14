var STATUS_OK = 1;
var STATUS_WARN = 2;
var STATUS_UNKNOWN = 3;

var createEnvironmentComponent = function(data) {
    var container = $(document.createElement("DIV"));
    container.addClass("env");

    var title = $(document.createElement("H2"));
    title.text(data.name);
    container.append(title);

    var statusLabel = $(document.createElement("DIV"));
    container.append(statusLabel);

    var versionLabel = $(document.createElement("DIV"));
    container.append(versionLabel);

    var obj = {};
    obj.updateServerStatus = function(data) {
        var numServers = 0, numOk = 0, numWarning = 0, numUnknown = 0;
        var versions = {};
        Object.keys(data.servers).forEach(function (serverName, i) {
            var server = data.servers[serverName];

            numServers++;
            if (server.status == "OK") {
                numOk++;
            } else if (server.status == "WARNING") {
                numWarning++;
            } else if (server.status == "Unknown") {
                numUnknown++;
            } else {
                numUnknown++;
            }

            if (typeof versions[server.version] === "undefined") {
                versions[server.version] = 1;
            } else {
                versions[server.version]++;
            }
        });

        if (numOk == numServers) {
            statusLabel.text(numServers + " OK");
            this.setStatus(STATUS_OK);
        } else if (numWarning == numServers) {
            statusLabel.text(numServers + " Warning");
            this.setStatus(STATUS_WARN);
        } else if (numUnknown == numServers) {
            statusLabel.text(numServers + " Unknown");
            this.setStatus(STATUS_UNKNOWN);
        } else {
            this.setStatus(STATUS_WARN);
            statusLabel.text(numOk + " OK, " + numWarning + " WARNING, " + numUnknown + " UNKNOWN");
        }

        var versionNumbers = Object.keys(versions);
        if (versionNumbers.length == 1) {
            versionLabel.text(versionNumbers[0]);
        } else {
            var versionData = [];
            for (var i = 0; i < versionNumbers.length; i++) {
                var version = versionNumbers[i];
                var count = versions[version];
                versionData.push(count + " " + version);
            }
            versionLabel.text(versionData.join(", "));
        }
    };
    obj.setStatus = function(s) {
        this.container.removeClass("status-ok");
        this.container.removeClass("status-warn");
        this.container.removeClass("status-unknown");
        if (s == STATUS_OK) {
            this.container.addClass("status-ok");
        } else if (s == STATUS_WARN) {
            this.container.addClass("status-warn");
        } else {
            this.container.addClass("status-unknown");
        }
    };
    obj.name = data.name;
    obj.container = container;

    return obj;
}

var createChart = function(container) {

    var margin = {
        top: 20,
        bottom: 30,
        left: 20,
        right: 30
    };

    var loadWidth = 20;
    var mainChartLeft = 2*margin.left + loadWidth;

    var serverColors = d3.scaleOrdinal(d3.schemeCategory10);

    var svg = d3.select(container)
        .append("svg");

    var loadGroup = svg.append("g")
        .attr("class", "load")
        .attr("transform", "translate(" + margin.left + ", " + margin.top + ")");

    var chartArea = svg.append("g")
        .attr("class", "chart-area")
        .attr("transform", "translate(" + mainChartLeft + ", " + margin.right + ")");

    var obj = {};
    obj.update = function() {
        this.width = container.offsetWidth;
        this.height = container.offsetHeight;

        this.usableWidth = this.width - margin.left - margin.right;
        this.usableHeight = this.height - margin.top - margin.bottom;

        this.chartAreaWidth = this.width - mainChartLeft - margin.right;

        svg.attr("width", this.width)
           .attr("height", this.height);
    };
    obj.updateLoad = function(data) {
        var scale = d3.scaleLinear()
            .domain([0, 1])
            .range([0, this.usableHeight]);

        var total = d3.sum(data, function(d) { return d.load });

        var offset = 0;
        data.forEach(function(d) {
            d.height = scale(d.load/total);
            d.x0 = offset;
            offset += d.height;
        });

        var updateSel = loadGroup.selectAll("rect.server")
            .data(data);

        updateSel.transition()
            .duration(500)
            .attr("y", function(d) { return d.x0; })
            .attr("height", function(d) { return d.height; });

        updateSel.enter()
            .append("rect")
            .attr("class", "server")
            .attr("x", 0)
            .attr("width", loadWidth)
            .attr("y", function(d) { return d.x0; })
            .attr("height", function(d) { return d.height; })
            .style("fill", function(d) { return serverColors(d.host); })
            .style("stroke", "transparent");

        updateSel.exit().remove();
    };
    obj.updateExceptions = function(data) {
        var currentHour = new Date().getHours();
        var buildYesterdayFromHour = function(hour) {
            var d = new Date(new Date().getTime()-86400*1000);
            d.setHours(hour);
            d.setMinutes(0);
            d.setSeconds(0);
            return d;
        };
        var buildTodayFromHour = function(hour) {
            var d = new Date();
            d.setHours(hour);
            d.setMinutes(0);
            d.setSeconds(0);
            return d;
        };
        data.forEach(function(d) {
            if (d.time > currentHour) {
                d.time = buildYesterdayFromHour(d.time);
            } else {
                d.time = buildTodayFromHour(d.time);
            }
        });

        data.sort(function(a,b) { return a.time >= b.time ? 1 : -1; });

        var timeRange = d3.extent(data, function(d) { return d.time; });
        var currentMax = d3.max(data, function(d) { return d.value; });

        if (currentMax == 0) {
            currentMax = 10;
        }

        var xScale = d3.scaleTime()
            .domain(timeRange)
            .range([0, this.chartAreaWidth]);

        var exceptionScale = d3.scaleLinear()
            .domain([currentMax, 0])
            .range([0, this.usableHeight]);

        var timeAxis = d3.axisBottom(xScale)
            .ticks(24)
            .tickFormat(function(d) { return d.getHours(); });

        if (!this.timeAxis) {
            this.timeAxis = chartArea.append("g")
                .attr("class", "x-axis")
                .attr("transform", "translate(0, " + this.usableHeight + ")")
                .call(timeAxis);
        } else {
            this.timeAxis.transition()
                .duration(500)
                .call(timeAxis);
        }

        if (!this.exceptionAxis) {
            this.exceptionAxis = chartArea.append("g")
                .attr("class", "y-axis")
                .attr("transform", "translate(" + this.chartAreaWidth + ", 0)")
                .call(d3.axisRight(exceptionScale));
        } else {
            this.exceptionAxis.transition()
                .duration(500)
                .call(d3.axisRight(exceptionScale));
        }

        var exceptionLine = d3.line()
            .x(function(d) { return xScale(d.time); })
            .y(function(d) { return exceptionScale(d.value); });

        if (!this.exceptionPath) {
            this.exceptionPath = chartArea.datum(data)
                .append("path")
                .attr("class", "exception-line")
                .attr("d", exceptionLine)
                .style("stroke", "crimson")
                .style("stroke-width", "2px")
                .style("fill", "transparent");
        } else {
            this.exceptionPath.transition()
                .duration(500)
                .attr("d", exceptionLine);
        }
    };
    return obj;
};

var createApplicationComponent = function(appName) {

    var container = $(document.createElement("DIV"));
    container.addClass("app");

    var topRow = $(document.createElement("DIV"));
    topRow.addClass("top-row");

    var nameCell = $(document.createElement("DIV"));
    nameCell.addClass("name-cell");

    var title = $(document.createElement("H1"));
    title.text(appName);
    nameCell.append(title);

    var buildVersion = $(document.createElement("DIV"));
    buildVersion.addClass("build-version");
    buildVersion.text("Build: Unknown");
    nameCell.append(buildVersion);

    topRow.append(nameCell);

    container.append(topRow);

    var chartHolder = $(document.createElement("DIV"));
    chartHolder.addClass("chart-holder");

    var chart = createChart(chartHolder[0]);

    container.append(chartHolder);

    var obj = {};
    obj.envs = {};
    obj.name = appName;
    obj.container = container;
    obj.chart = chart;
    obj.updateServerStatus = function(data) {
        data.environments.forEach(function (envData) {
            var env = obj.envs[envData.name];
            if (typeof env == "undefined") {
                env = createEnvironmentComponent(envData);
                obj.envs[envData.name] = env;
                topRow.append(env.container);
            }

            env.updateServerStatus(envData);
        });
    };
    obj.updateBuildStatus = function(data) {
        buildVersion.text("Build: " + data.buildnumber);
        nameCell.removeClass("status-ok");
        nameCell.removeClass("status-warn");
        nameCell.removeClass("status-unknown");
        if (data.status == "OK") {
            nameCell.addClass("status-ok");
        } else if (data.status == "ERROR") {
            nameCell.addClass("status-warn");
        } else {
            nameCell.addClass("status-unknown");
        }
    };

    return obj;
}

var createMonitorComponent = function(appContainer) {
    var obj = {};
    obj.applications = {};
    obj.getOrCreateApplication = function(appName) {
        var app = obj.applications[appName];
        if (typeof app == "undefined") {
            app = createApplicationComponent(appName);
            obj.applications[appName] = app;
            appContainer.append(app.container);
            app.chart.update();
        }

        return app;
    };
    obj.updateBuildStatus = function() {
        $.getJSON("/builds", function (applications) {
            applications.sort(function(a,b) {
                return a.name >= b.name ? 1 : -1;
            });
            applications.forEach(function (appData) {
                var app = obj.getOrCreateApplication(appData.name);
                app.updateBuildStatus(appData);
            });
        });
    };
    obj.updateProbes = function() {
        $.getJSON("/probes", function (applications) {
            applications.sort(function(a,b) {
                return a.name >= b.name ? 1 : -1;
            });
            applications.forEach(function (appData) {
                var app = obj.getOrCreateApplication(appData.name);
                app.updateServerStatus(appData);
            });
        });
    };
    obj.updateLoad = function() {
        Object.keys(this.applications).forEach(function(name) {
            var app = this.applications[name];
            $.getJSON("/logs/" + name + "/application-load", function (data) {
                app.chart.updateLoad(data.loadSeries);
            });
        }.bind(this));
    };
    obj.updateExceptions = function() {
        Object.keys(this.applications).forEach(function(name) {
            var app = this.applications[name];
            $.getJSON("/logs/" + name + "/exception", function (data) {
                app.chart.updateExceptions(data.timeSeries);
            });
        }.bind(this));
    };
    obj.monitor = function() {
        console.log("Updating");
        this.updateProbes();
        this.updateBuildStatus();

        // Upload charts slightly later
        setTimeout(function() {
            this.updateLoad();
            this.updateExceptions();
        }.bind(this), 1000);

        setTimeout(this.monitor.bind(this), 30000);
    };
    return obj;
};

$(window).load(function () {
    var monitor = createMonitorComponent($("#app-container"));
    monitor.monitor();
});
