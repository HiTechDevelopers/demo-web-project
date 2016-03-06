var app = angular.module('app', ['ngAnimate', 'ngAria', 'ngMaterial', 'ui.grid'])

// Define directives here
app.directive('tabset', function () {
  return {
    restrict: 'E',
    transclude: true,
    templateUrl: 'tabset.html',
    bindToController: true,
    controllerAs: 'tabset',
    controller: function () {
      var self = this
      self.tabs = []
      self.addTab = function addTab (tab) {
        self.tabs.push(tab)

        if (self.tabs.length === 1) {
          tab.active = true
        }
      }
      self.select = function (selectedTab) {
        angular.forEach(self.tabs, function (tab) {
          if (tab.active && tab !== selectedTab) {
            tab.active = false
          }
        })
        selectedTab.active = true
      }
    }
  }
})

app.directive('tab', function () {
  return {
    restrict: 'E',
    transclude: true,
    template: '<div role="tabpanel" ng-show="active" ng-transclude></div>',
    require: '^tabset',
    scope: {
      heading: '@'
    },
    link: function (scope, elem, attr, tabsetCtrl, contentCtrl) {
      scope.active = false
      tabsetCtrl.addTab(scope)
    }
  }
})

app.directive('content', function () {
  return {
    restrict: 'E',
    transclude: true,
    template: '<div role="contentarea" ng-show="active" ng-transclude></div>'
  }
})

app.controller('mytable', function ($scope, $http) {
  $scope.gridOptions = {}
  $scope.gridOptions = {
    columnDefs: [
      {field: 'date', displayName: 'Date', width: '20.79%', minWidth: 50, maxWidth: 200},
      {field: 'launchVehicle', displayName: 'Vehicle', width: '10.4%', minWidth: 50, maxWidth: 100},
      {field: 'launchLocation', displayName: 'Location', width: '34.82%', minWidth: 50, maxWidth: 335},
      {field: 'payload', displayName: 'Payload', width: '21.83%', minWidth: 50, maxWidth: 210},
      {field: 'description', displayName: 'More Info', cellTooltip: '{{row.entity.description}}',
        cellTemplate: '<div class="ui-grid-cell-contents"><md-content><md-button><md-icon md-svg-src="./img/ic_info_outline_black_24px.svg" style="width: 24px; height: 24px; margin-right: 20; margin:bottom: 10;"></md-icon><md-tooltip md-direction="left" style="width: 300px; height: 300px;">{{row.entity.description}}</md-tooltip></md-button><md-content></div>',
        width: '10.4%', minWidth: 75, maxWidth: 100}
    ],
    rowHeight: '2.5%'
  }

  $scope.loadData = function () {
    $scope.launches = []
    $http.get('./upcoming_launches.json')
      .success(function (data) {
        $scope.gridOptions.data = data
      })
  }

  $scope.loadData()
})

app.directive('mytable', function () {
  return {
    restrict: 'E',
    templateUrl: 'mytable.html',
    bindToController: true,
    controllerAs: 'mytable'
  }
})

app.controller('mytable2', function ($scope, $http) {
  $scope.gridOptions2 = {}
  $scope.gridOptions2 = {
    columnDefs: [
    {field: 'date', displayName: 'Date', width: '20.79%', minWidth: 50, maxWidth: 200},
    {field: 'launchVehicle', displayName: 'Vehicle', width: '10.4%', minWidth: 50, maxWidth: 100},
    {field: 'launchLocation', displayName: 'Location', width: '34.82%', minWidth: 50, maxWidth: 335},
    {field: 'payload', displayName: 'Payload', width: '21.83%', minWidth: 50, maxWidth: 210},
    {field: 'description', displayName: 'More Info', cellTooltip: '{{row.entity.description}}',
      cellTemplate: '<div class="ui-grid-cell-contents"><md-content><md-button><md-icon md-svg-src="./img/ic_info_outline_black_24px.svg" style="width: 24px; height: 24px; margin-right: 20; margin:bottom: 10;"></md-icon><md-tooltip md-direction="left" style="width: 300px; height: 300px;">{{row.entity.description}}</md-tooltip></md-button><md-content></div>',
      width: '10.4%', minWidth: 75, maxWidth: 100}
    ],
    rowHeight: '2.5%'
  }

  $scope.loadData2 = function () {
    $scope.launches = []
    $http.get('./past_launches.json')
      .success(function (data) {
        $scope.gridOptions2.data = data
      })
  }

  $scope.loadData2()
})

app.directive('mytable2', function () {
  return {
    restrict: 'E',
    templateUrl: 'mytable2.html',
    bindToController: true,
    controllerAs: 'mytable2'
  }
})

app.controller('twitterWidget', function ($scope) {
	function loadTwitter() {
		!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+"://platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");
	}

	var twitter = document.getElementById('twitter-wjs');
	if (twitter) twitter.remove();
	loadTwitter(); 
 })
