# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.28

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:

#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:

# Disable VCS-based implicit rules.
% : %,v

# Disable VCS-based implicit rules.
% : RCS/%

# Disable VCS-based implicit rules.
% : RCS/%,v

# Disable VCS-based implicit rules.
% : SCCS/s.%

# Disable VCS-based implicit rules.
% : s.%

.SUFFIXES: .hpux_make_needs_suffix_list

# Command-line flag to silence nested $(MAKE).
$(VERBOSE)MAKESILENT = -s

#Suppress display of executed commands.
$(VERBOSE).SILENT:

# A target that is always out of date.
cmake_force:
.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /usr/bin/cmake

# The command to remove a file.
RM = /usr/bin/cmake -E rm -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/tim/projects/fifty/04_game_of_life

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/tim/projects/fifty/04_game_of_life/build

# Include any dependencies generated for this target.
include CMakeFiles/game_of_life.dir/depend.make
# Include any dependencies generated by the compiler for this target.
include CMakeFiles/game_of_life.dir/compiler_depend.make

# Include the progress variables for this target.
include CMakeFiles/game_of_life.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/game_of_life.dir/flags.make

CMakeFiles/game_of_life.dir/src/main.cpp.o: CMakeFiles/game_of_life.dir/flags.make
CMakeFiles/game_of_life.dir/src/main.cpp.o: /home/tim/projects/fifty/04_game_of_life/src/main.cpp
CMakeFiles/game_of_life.dir/src/main.cpp.o: CMakeFiles/game_of_life.dir/compiler_depend.ts
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green --progress-dir=/home/tim/projects/fifty/04_game_of_life/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/game_of_life.dir/src/main.cpp.o"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -MD -MT CMakeFiles/game_of_life.dir/src/main.cpp.o -MF CMakeFiles/game_of_life.dir/src/main.cpp.o.d -o CMakeFiles/game_of_life.dir/src/main.cpp.o -c /home/tim/projects/fifty/04_game_of_life/src/main.cpp

CMakeFiles/game_of_life.dir/src/main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Preprocessing CXX source to CMakeFiles/game_of_life.dir/src/main.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/tim/projects/fifty/04_game_of_life/src/main.cpp > CMakeFiles/game_of_life.dir/src/main.cpp.i

CMakeFiles/game_of_life.dir/src/main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Compiling CXX source to assembly CMakeFiles/game_of_life.dir/src/main.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/tim/projects/fifty/04_game_of_life/src/main.cpp -o CMakeFiles/game_of_life.dir/src/main.cpp.s

CMakeFiles/game_of_life.dir/src/Button.cpp.o: CMakeFiles/game_of_life.dir/flags.make
CMakeFiles/game_of_life.dir/src/Button.cpp.o: /home/tim/projects/fifty/04_game_of_life/src/Button.cpp
CMakeFiles/game_of_life.dir/src/Button.cpp.o: CMakeFiles/game_of_life.dir/compiler_depend.ts
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green --progress-dir=/home/tim/projects/fifty/04_game_of_life/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building CXX object CMakeFiles/game_of_life.dir/src/Button.cpp.o"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -MD -MT CMakeFiles/game_of_life.dir/src/Button.cpp.o -MF CMakeFiles/game_of_life.dir/src/Button.cpp.o.d -o CMakeFiles/game_of_life.dir/src/Button.cpp.o -c /home/tim/projects/fifty/04_game_of_life/src/Button.cpp

CMakeFiles/game_of_life.dir/src/Button.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Preprocessing CXX source to CMakeFiles/game_of_life.dir/src/Button.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/tim/projects/fifty/04_game_of_life/src/Button.cpp > CMakeFiles/game_of_life.dir/src/Button.cpp.i

CMakeFiles/game_of_life.dir/src/Button.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Compiling CXX source to assembly CMakeFiles/game_of_life.dir/src/Button.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/tim/projects/fifty/04_game_of_life/src/Button.cpp -o CMakeFiles/game_of_life.dir/src/Button.cpp.s

CMakeFiles/game_of_life.dir/src/Rules.cpp.o: CMakeFiles/game_of_life.dir/flags.make
CMakeFiles/game_of_life.dir/src/Rules.cpp.o: /home/tim/projects/fifty/04_game_of_life/src/Rules.cpp
CMakeFiles/game_of_life.dir/src/Rules.cpp.o: CMakeFiles/game_of_life.dir/compiler_depend.ts
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green --progress-dir=/home/tim/projects/fifty/04_game_of_life/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Building CXX object CMakeFiles/game_of_life.dir/src/Rules.cpp.o"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -MD -MT CMakeFiles/game_of_life.dir/src/Rules.cpp.o -MF CMakeFiles/game_of_life.dir/src/Rules.cpp.o.d -o CMakeFiles/game_of_life.dir/src/Rules.cpp.o -c /home/tim/projects/fifty/04_game_of_life/src/Rules.cpp

CMakeFiles/game_of_life.dir/src/Rules.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Preprocessing CXX source to CMakeFiles/game_of_life.dir/src/Rules.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/tim/projects/fifty/04_game_of_life/src/Rules.cpp > CMakeFiles/game_of_life.dir/src/Rules.cpp.i

CMakeFiles/game_of_life.dir/src/Rules.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Compiling CXX source to assembly CMakeFiles/game_of_life.dir/src/Rules.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/tim/projects/fifty/04_game_of_life/src/Rules.cpp -o CMakeFiles/game_of_life.dir/src/Rules.cpp.s

# Object files for target game_of_life
game_of_life_OBJECTS = \
"CMakeFiles/game_of_life.dir/src/main.cpp.o" \
"CMakeFiles/game_of_life.dir/src/Button.cpp.o" \
"CMakeFiles/game_of_life.dir/src/Rules.cpp.o"

# External object files for target game_of_life
game_of_life_EXTERNAL_OBJECTS =

game_of_life: CMakeFiles/game_of_life.dir/src/main.cpp.o
game_of_life: CMakeFiles/game_of_life.dir/src/Button.cpp.o
game_of_life: CMakeFiles/game_of_life.dir/src/Rules.cpp.o
game_of_life: CMakeFiles/game_of_life.dir/build.make
game_of_life: /usr/lib/x86_64-linux-gnu/libSDL2.so
game_of_life: /usr/lib/x86_64-linux-gnu/libSDL2_image.so
game_of_life: /usr/lib/x86_64-linux-gnu/libSDL2_ttf.so
game_of_life: CMakeFiles/game_of_life.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green --bold --progress-dir=/home/tim/projects/fifty/04_game_of_life/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_4) "Linking CXX executable game_of_life"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/game_of_life.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/game_of_life.dir/build: game_of_life
.PHONY : CMakeFiles/game_of_life.dir/build

CMakeFiles/game_of_life.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/game_of_life.dir/cmake_clean.cmake
.PHONY : CMakeFiles/game_of_life.dir/clean

CMakeFiles/game_of_life.dir/depend:
	cd /home/tim/projects/fifty/04_game_of_life/build && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/tim/projects/fifty/04_game_of_life /home/tim/projects/fifty/04_game_of_life /home/tim/projects/fifty/04_game_of_life/build /home/tim/projects/fifty/04_game_of_life/build /home/tim/projects/fifty/04_game_of_life/build/CMakeFiles/game_of_life.dir/DependInfo.cmake "--color=$(COLOR)"
.PHONY : CMakeFiles/game_of_life.dir/depend

