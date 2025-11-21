
# Minesweeper — A Scala Study Project

[![Coverage Status](https://coveralls.io/repos/github/Atomarverseucht/minesweeper/badge.svg?branch=main)](https://coveralls.io/github/Atomarverseucht/minesweeper)

A polished, study-focused implementation of the classic Minesweeper game — written primarily in Scala with a touch of shell tooling. This repository is a joint learning project by [Atomarverseucht](https://github.com/Atomarverseucht) and [Guakocius](https://github.com/Guakocius). We build clear algorithms, idiomatic Scala, and a compact, testable codebase while keeping the spirit of the original game.

## Badges

| Used Languages | Tested Operating Systems | Tech Stack |
| :--: | :--: | :--: |
| ![Scala](https://img.shields.io/badge/Scala-DC322F?style=for-the-badge&logo=scala&logoColor=white) ![Shell](https://img.shields.io/badge/Shell_Script-121011?style=for-the-badge&logo=gnu-bash&logoColor=white) | ![Arch](https://img.shields.io/badge/Arch_Linux-1793D1?style=for-the-badge&logo=arch-linux&logoColor=white) ![Ubuntu](https://img.shields.io/badge/Ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=white) ![Windows_11](https://img.shields.io/badge/Windows_11-0078d4?style=for-the-badge&logo=windows&logoColor=white) | ![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white) ![sbt](https://img.shields.io/badge/sbt-0095D5?style=for-the-badge&logo=sbt&logoColor=white) |

Additional quick badges:
[![License](https://img.shields.io/badge/License-check?style=flat&logo=open-source-initiative)](./LICENSE)
[![Repo Size](https://img.shields.io/github/repo-size/Atomarverseucht/minesweeper?style=flat)](https://github.com/Atomarverseucht/minesweeper)
[![Stars](https://img.shields.io/github/stars/Atomarverseucht/minesweeper?style=social)](https://github.com/Atomarverseucht/minesweeper/stargazers)

---

Highlights
- Elegant Scala implementation of Minesweeper logic (board generation, adjacency counting, reveal/flag mechanics).
- Deterministic board generation options for repeatable tests.
- Unit tests that cover core game rules and edge cases.
- Small, easy-to-read codebase suitable for learning Scala idioms.

Features
- Classic Minesweeper rules implemented in Scala
- Deterministic RNG option for reproducible examples and tests
- Unit and property tests for core logic
- Small helper shell scripts (where present) to ease common tasks

Tech stack and environment
- Language: Scala (≈99.7%)
- Auxiliary: Shell scripts (≈0.3%)
- Build: sbt (recommended)
- Tested on: Arch Linux, Ubuntu, Windows 11 (community-tested)

Getting started (local)
Prerequisites
- JDK 8+ (OpenJDK or Oracle)
- sbt (recommended) or another Scala build tool

Build and run (typical)
```bash
# clone the repo
git clone https://github.com/Atomarverseucht/minesweeper.git
cd minesweeper

# compile
sbt compile

# run (if project has an entrypoint)
sbt run

# run tests
sbt test
```

Project layout (high level)
- src/main/scala — core game implementation
- src/test/scala — unit tests
- scripts/ — small shell helpers (if present)
- README.md — this file

Contributing
We welcome contributions, suggestions, and bug reports. If you'd like to contribute:
1. Fork the repository.
2. Create a topic branch: git checkout -b feature/your-feature
3. Make small, focused commits.
4. Add or update tests where appropriate.
5. Open a pull request describing the change.

Branching model
- main: stable, release-ready code
- dev: in-progress features and experiments
- feature/*: short-lived branches for isolated features or fixes

Roadmap (short)
- Improve docs and examples
- Add more explanatory tests and sample game sessions
- Optional: small web or GUI front-end to demonstrate the engine

Authors & acknowledgements
- Atomarverseucht — project owner and co-author — https://github.com/Atomarverseucht
- Guakocius — co-author and collaborator — https://github.com/Guakocius

License
See the LICENSE file in the repository for licensing information. If the repository does not contain a LICENSE, please contact the maintainers before using the code in production.

Contact
For questions, issues, or suggestions: open an issue or reach out to the repository owners via their GitHub profiles.

Enjoy exploring the code — may your flags be true and your guesses few! ⚑💣
```
