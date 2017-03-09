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
            versionLabel.text(", ".join(versionData));
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

var createApplicationComponent = function(appName) {

    var container = $(document.createElement("DIV"));
    container.addClass("app");

    var topRow = $(document.createElement("DIV"));
    topRow.addClass("top-row");

    var title = $(document.createElement("H1"));
    title.text(appName);
    topRow.append(title);

    container.append(topRow);

    var chartHolder = $(document.createElement("DIV"));
    chartHolder.addClass("chart-holder");

    chartHolder.text("FOO");

    container.append(chartHolder);

    var obj = {};
    obj.envs = {};
    obj.name = appName;
    obj.container = container;
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
        title.removeClass("status-ok");
        title.removeClass("status-warn");
        title.removeClass("status-unknown");
        if (data.status == "OK") {
            title.addClass("status-ok");
        } else if (data.status == "ERROR") {
            title.addClass("status-warn");
        } else {
            title.addClass("status-unknown");
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
        }

        return app;
    };
    obj.updateBuildStatus = function() {
        $.getJSON("/builds", function (applications) {
            applications.forEach(function (appData) {
                var app = obj.getOrCreateApplication(appData.name);
                app.updateBuildStatus(appData);
            });
        });
    };
    obj.updateProbes = function() {
        $.getJSON("/probes", function (applications) {
            applications.forEach(function (appData) {
                var app = obj.getOrCreateApplication(appData.name);
                app.updateServerStatus(appData);
            });
        });
    };
    obj.monitor = function() {
        console.log("Updating");
        this.updateProbes();
        this.updateBuildStatus();
        setTimeout(this.monitor.bind(this), 30000);
    };
    return obj;
};

$(window).load(function () {
    var monitor = createMonitorComponent($("#app-container"));
    monitor.monitor();
});
