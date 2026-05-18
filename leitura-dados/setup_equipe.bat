@echo off
cls
echo ==============================================
echo   Iniciando Setup Automatico - Tech Music Team
echo ==============================================
echo.

:: PASSO 1: CONFIGURAR VARIAVEL DE AMBIENTE
echo [1/3] Configurando variaveis do projeto...
setx MUSIC_APP_ENV "development" >nul
echo [OK] Ambiente configurado para 'development'!
echo.

:: PASSO 2: VERIFICAR JAVA
echo [2/3] Verificando o motor do projeto (Java)...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERRO] Java nao detectado! Instale o JDK 17.
) else (
    echo [OK] Java esta instalado e funcionando!
)
echo.

:: PASSO 3: VERIFICAR MAVEN (LIBS)
echo [3/3] Verificando gerenciador de Libs (Maven)...
call mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERRO] Maven nao detectado! O IntelliJ nao vai conseguir baixar as Libs.
) else (
    echo [OK] Maven detectado com sucesso!
)
echo.

echo ==============================================
echo Setup concluido! Se nao houveram erros acima,
echo voce ja pode abrir o projeto no IntelliJ.
echo ==============================================
pause