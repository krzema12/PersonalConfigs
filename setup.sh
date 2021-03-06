DOTFILES="~/PersonalConfigs/dotfiles"

if [ ! -d ~/PersonalConfigs ]
then
    echo "Cloning repo with configs..."
    cd ~
    git clone git@github.com:krzema12/PersonalConfigs.git
else
    echo "No need to clone repo"
fi

SHRC_SOURCE="source $DOTFILES/shrc"
if ! grep -Fxq "$SHRC_SOURCE" ~/.bashrc
then
    echo "Adding shrc..."
    echo $SHRC_SOURCE >> ~/.bashrc
    echo "Remember to source the new .bashrc, i.e. call: source ~/.bashrc"
else
    echo "No need to add shrc"
fi

VIMRC_SOURCE="source $DOTFILES/vimrc"
if ! grep -Fxq "$VIMRC_SOURCE" ~/.vimrc
then
    echo "Adding vimrc..."
    echo $VIMRC_SOURCE >> ~/.vimrc
else
    echo "No need to add vimrc"
fi

GITCONFIG_SOURCE="path = $DOTFILES/gitconfig"
if ! grep -Fxq "$GITCONFIG_SOURCE" ~/.gitconfig
then
    echo "Adding gitconfig..."
    echo "[include]" >> ~/.gitconfig
    echo $GITCONFIG_SOURCE >> ~/.gitconfig
else
    echo "No need to add gitconfig"
fi
