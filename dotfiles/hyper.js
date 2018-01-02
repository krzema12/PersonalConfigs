// Future versions of Hyper may add additional config options,
// which will not automatically be merged into this file.
// See https://hyper.is#cfg for all currently supported options.

module.exports = {
  config: {
    updateChannel: 'stable',

    fontSize: 12, // In pixels.
    fontFamily: 'Menlo, "DejaVu Sans Mono", Consolas, "Lucida Console", monospace',

    padding: '12px 14px',

    cursorColor: 'rgba(0,255,0,0.8)',
    cursorShape: 'BLOCK',
    cursorBlink: false,

    foregroundColor: '#fff',
    backgroundColor: '#000',
    borderColor: '#0F0',
    colors: {
      black: '#000000',
      red: '#ff0000',
      green: '#33ff00',
      yellow: '#ffff00',
      blue: '#0066ff',
      magenta: '#cc00ff',
      cyan: '#00ffff',
      white: '#d0d0d0',
      lightBlack: '#808080',
      lightRed: '#ff0000',
      lightGreen: '#33ff00',
      lightYellow: '#ffff00',
      lightBlue: '#0066ff',
      lightMagenta: '#cc00ff',
      lightCyan: '#00ffff',
      lightWhite: '#ffffff'
    },

    css: '',
    termCSS: '',

    showHamburgerMenu: '',
    showWindowControls: '',

    shell: '', // Empty: use system's login shell.
    shellArgs: ['--login'],
    env: {},

    bell: false, // No bell.
    copyOnSelect: false,

    // for advanced config flags please refer to https://hyper.is/#cfg
  },

  plugins: [
    'hyperlinks',
    'gitrocket',
    'hyper-solarized-dark',
    'hyper-tab-icons', // Requires setting command name in the window title. See https://github.com/zeit/hyper/issues/1188#issuecomment-267301723
    'hyper-search',
  ],

  keymaps: {
    'window:devtools': 'cmd+alt+o',
  }
};
