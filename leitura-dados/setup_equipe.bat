@echo off
title Setup Tech Music Team

:menu
cls
echo ==============================================
echo     MENU TECH MUSIC TEAM - AUTOMACAO
echo ==============================================
echo [1] Configurar Variaveis do Projeto
echo [2] Verificar Instalacoes (Java e Maven)
echo [3] Sair
echo ==============================================
set /p opcao="Escolha uma opcao (1, 2 ou 3): "


if "%opcao%"=="1" goto config
if "%opcao%"=="2" goto verificar
if "%opcao%"=="3" goto sair


echo.
echo Opcao invalida! Tente novamente.
pause
goto menu

:: ==============================================
:: BLOCO 1: CONFIGURACAO
:: ==============================================
:config
cls
echo --- CONFIGURANDO AMBIENTE ---
echo.
echo Injetando variaveis de ambiente para a equipe...
setx MUSIC_APP_ENV "development"
echo.
echo [OK] Variaveis configuradas com sucesso!
pause
goto menu

:: ==============================================
:: BLOCO 2: VERIFICACAO
:: ==============================================
:verificar
cls
echo --- VERIFICANDO INSTALACOES ---
echo.

echo [Java]
java -version
echo.

echo [Maven]
mvn -version
echo.

echo Verificacao concluida! Se apareceram erros acima, instale os programas.
pause
goto menu

:: ==============================================
:: BLOCO 3: SAIR
:: ==============================================
:sair
echo.
echo Obrigado por usar o Setup da Tech Music Team. Bora codar!
pause >nul