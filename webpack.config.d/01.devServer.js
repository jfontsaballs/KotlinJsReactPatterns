// wrap is useful, because declaring variables in module can be already declared
// module creates own lexical environment
;(function (config) {
    config.watchOptions = config.watchOptions || {}
    config.watchOptions.ignored = /.*\.kt$/
})(config);