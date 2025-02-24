# Swingy

A RPG game that conbines two views, a CLI view and a view with Swing. Following the MVC pattern and sqlite access for persistence.

## Usage

java -jar swingy <console|gui>

java -jar swingy --help'

## Behaviour

### Controls

The controls are straightforward, but here are some additional rules

CLI inputs do not distinguish between uppercase and lowercase

You can switch views at any time by entering 'v' or 'view'

You can exit the program at any time by entering 'e' or 'exit'

Most controls follow this rule. For example, you can type 'play' or 'p', 'remove' or 'r', move 'west' or 'w', etc.

To pick a class, it is recommended to use '1', '2', or '3', but you can also type the name of the class.

To accept or reject an artifact, you can use inputs like 'yes', 'no', and other variants.

GUI Controls are intuitive, just click the right button.

### Gameplay

You create and pick a hero.

You move across the map, which is a rectangular grid.

When you encounter an enemy, you can choose to run (50% chance) or fight.

If you win the fight, there's a chance you obtain an artifact to increase your stats. If you lose, your hero is deleted.

When you reach the edge of the map, you win.

### Classes

Warrior: Very strong, designed to defeat almost all enemies.

Enchanter: Gains double stats from artifacts.

Peasant: Weak, 'real' gameplay.

### Hero Level

A hero levels up upon reaching the experience threshold: 'level * 1000 + (level - 1)^2 * 450'.

When you level up, your stats increase by one point.

### Stats

There are three main stats: attack, hit points, and defense.

### Fight Outcomes

Fight outcomes are determined by the number of hits both combatants need to eliminate each other.

(HP / (ATK - opponent's DEF)).

The combatant with the fewest required hits, plus a bit of luck, wins.

### Artifact Stats

Artifact stats are based on the enemy level, plus or minus a luck factor.

### Enemies

Enemy levels are determined based on hero level, with a luck factor added or subtracted.

Enemy stats are solely based on their level.

### Map

Map size is determined by hero level using the formula: (level - 1) * 5 + 10 - (level % 2).

The density of enemies and artifacts remains constant across levels.

Enemies can spawn at the edge of the map.
