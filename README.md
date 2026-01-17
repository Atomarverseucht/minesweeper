
# Winesmeeper

[![Coverage Status](https://coveralls.io/repos/github/Atomarverseucht/minesweeper/badge.svg?branch=main)](https://coveralls.io/github/Atomarverseucht/minesweeper)

A polished, study-focused implementation of the classic Minesweeper game, written primarily in Scala with a touch of shell tooling. This repository is a joint learning project by [Atomarverseucht](https://github.com/Atomarverseucht) and [Guakocius](https://github.com/Guakocius).<br>
The graphics of the GUI is made by [modelivesky](https://modelivesky.com/), if you want to download it click [here](https://modelivesky.itch.io/minesweeperpack)

| Used Languages | Tested Operating Systems | Tech Stack |
| :--: | :--: | :--: |
| ![Scala](https://img.shields.io/badge/Scala-DC322F?style=for-the-badge&logo=scala&logoColor=white) ![Shell](https://img.shields.io/badge/Shell_Script-121011?style=for-the-badge&logo=gnu-bash&logoColor=white) | ![Arch](https://img.shields.io/badge/Arch_Linux-1793D1?style=for-the-badge&logo=arch-linux&logoColor=white) ![Ubuntu](https://img.shields.io/badge/Ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=white) ![Windows_11](https://img.shields.io/badge/Windows_11-0078d4?style=for-the-badge&logo=windows&logoColor=white) | ![sbt](https://img.shields.io/badge/sbt-0095D5?style=for-the-badge&logo=sbt&logoColor=white) ![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white) ![Kubernetes](https://img.shields.io/badge/Kubernetes-3069DE?style=for-the-badge&logo=kubernetes&logoColor=white) ![SonarQube](https://img.shields.io/badge/Sonarqube-5190cf?style=for-the-badge&logo=sonarqube&logoColor=white) |

[![License](https://img.shields.io/badge/License-check?style=flat&logo=open-source-initiative)](./LICENSE)
[![Contributing](https://img.shields.io/badge/Contributing-check?style=flat&logo=open-source-initiative)](./CONTRIBUTING.md)
[![Changelog](https://img.shields.io/badge/Changelog-check?style=flat&logo=open-source-initiative)](./CHANGELOG.md)
[![Repo Size](https://img.shields.io/github/repo-size/Atomarverseucht/minesweeper?style=flat)](https://github.com/Atomarverseucht/minesweeper)
[![Stars](https://img.shields.io/github/stars/Atomarverseucht/minesweeper?style=social)](https://github.com/Atomarverseucht/minesweeper/stargazers)

---

## How to play:
### TUI:
  - type "help" for getting a short explanation for each command

### GUI:
  - The buttons above are for the Sys-Commands
  - left-click on a field, to open a field
  - right-click on a field, to flag a field

## Getting started
Prerequisites
- JDK 11+ (OpenJDK or Oracle)
- for developing: sbt

How to play the game:
- download our newest [.jar](https://github.com/Atomarverseucht/minesweeper/releases/latest)
- execute following cmd
```bash
java -jar [path to .jar]
```

Develop with us with git and sbt:
```bash
# clone the repo
git clone https://github.com/Atomarverseucht/minesweeper.git
cd minesweeper

# compile
sbt compile

# run
sbt run

# run tests
sbt test
```

## Contributing
If you saw an issue with our project or you want to have a specific feature you can:
- make a Github-Issue and hope that we will fix your problem (or implement feature)
- develop the solution of your problem by yourself and open a pull request

## Branching model
- main: stable, release-ready code
- dev: instable development code
- feature/*: branches for experimenting with new features
  
## License
See the LICENSE file in the repository for licensing information.

Enjoy exploring the code — may your flags be true and your guesses few! ⚑💣
