PERSONAL_CONFIGS_PATH="~/PersonalConfigs/.personal_configs"

if [ ! -d ~/PersonalConfigs ]
then
    echo "Cloning repo with configs..."
    cd ~
    git clone git@github.com:krzema12/PersonalConfigs.git
else
    echo "No need to clone repo"
fi

VIMRC_SOURCE="source $PERSONAL_CONFIGS_PATH/.vimrc_private"
if ! grep -Fxq "$VIMRC_SOURCE" ~/.vimrc
then
    echo "Adding vimrc..."
    echo $VIMRC_SOURCE >> ~/.vimrc
else
    echo "No need to add vimrc"
fi

GITCONFIG_SOURCE="path = $PERSONAL_CONFIGS_PATH/.gitconfig_private"
if ! grep -Fxq "$GITCONFIG_SOURCE" ~/.gitconfig
then
    echo "Adding gitconfig..."
    echo "[include]" >> ~/.gitconfig
    echo $GITCONFIG_SOURCE >> ~/.gitconfig
else
    echo "No need to add gitconfig"
fi
