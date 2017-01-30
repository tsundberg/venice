function addPropertyListEntry(dl, name, value) {
    var nameElement = $(document.createElement("DT"));
    nameElement.text(name);
    dl.append(nameElement);

    var valueElement = $(document.createElement("DD"));
    valueElement.text(value);
    dl.append(valueElement);
}

function createEnvironmentContainer(env) {
    var container = $(document.createElement("DIV"));

    var title = $(document.createElement("H2"));
    title.text(env.name);
    container.append(title);

    var row = $(document.createElement("DIV"));
    row.addClass("row");

    Object.keys(env.servers).forEach(function (serverName, i) {
        var server = env.servers[serverName];

        var serverElement = $(document.createElement("DIV"));
        serverElement.addClass("col-sm-3");
        if (i > 0) {
            serverElement.addClass("col-sm-offset-1");
        }
        serverElement.addClass("server");
        if (server.status == "online") {
            serverElement.addClass("server-online");
        } else {
            serverElement.addClass("server-offline");
        }

        var serverNameElement = $(document.createElement("H3"));
        serverNameElement.text(serverName);
        serverElement.append(serverNameElement);

        var propertyList = $(document.createElement("DL"));
        addPropertyListEntry(propertyList, "Version", server.version);
        serverElement.append(propertyList);

        row.append(serverElement);
    });

    container.append(row);

    return container;
}

function createApplicationContainer(app) {
    var container = $(document.createElement("DIV"));

    var title = $(document.createElement("H1"));
    title.text(app.name);
    container.append(title);

    app.environments.forEach(function (env) {
        container.append(createEnvironmentContainer(env));
    });

    return container;
}

$(window).load(function () {
    var applications = [];

    var gfr = {};
    gfr.name = "GFR";
    gfr.environments = [];
    gfr.environments.push({
        "name": "production",
        "servers": {
            "L7700746": {
                "status": "online",
                "version": "4.6.401"
            },
            "L7700747": {
                "status": "online",
                "version": "4.6.401"
            },
            "L7700770": {
                "status": "offline",
                "version": "4.6.401"
            }
        }
    });
    gfr.environments.push({
        "name": "t2",
        "servers": {
            "L7700746": {
                "status": "online",
                "version": "4.6.401"
            },
            "L7700747": {
                "status": "online",
                "version": "4.6.401"
            }
        }
    });
    gfr.environments.push({
        "name": "t1",
        "servers": {
            "L7700746": {
                "status": "online",
                "version": "4.6.401"
            }
        }
    });
    gfr.environments.push({
        "name": "i1",
        "servers": {
            "L7700746": {
                "status": "online",
                "version": "4.6.401"
            }
        }
    });
    gfr.environments.push({
        "name": "u1",
        "servers": {
            "L7700746": {
                "status": "online",
                "version": "4.6.401"
            }
        }
    });

    applications.push(gfr);

    var appContainer = $("#app-container");
    applications.forEach(function (app) {
        appContainer.append(createApplicationContainer(app));
    });
});
