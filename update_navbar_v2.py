import sys

def update_file(file_path, old_text, new_text):
    with open(file_path, 'r') as f:
        content = f.read()
    if old_text in content:
        new_content = content.replace(old_text, new_text)
        with open(file_path, 'w') as f:
            f.write(new_content)
        print(f"Updated {file_path}")
    else:
        # Try to find a slightly different version for explore/index.html
        print(f"Could not find exact old_text in {file_path}")

# Explore update
explore_path = 'src/main/resources/templates/traveler/explore/index.html'
old_explore = """<nav class="navbar">

    <div class="container">

        <div class="logo">
            ◉ TRIPINAJA
        </div>

        <div class="nav-center">

            <a href="/dashboard">
                Dashboard
            </a>

            <a href="/explore"
               class="active">
                Explore
            </a>

        </div>

    </div>

</nav>"""

new_explore = """<nav class="navbar dashboard-navbar">

    <div class="container">

        <div class="logo">
            <span class="logo-icon">◉</span>
            TRIPINAJA
        </div>

        <div class="nav-center">
            <a href="/dashboard">Dashboard</a>
            <a href="/itinerary/list">My Trips</a>
        </div>

        <!-- PROFILE -->
        <a href="/profile" class="profile-menu">
            <div class="profile-avatar">A</div>
            <span class="profile-name">Akbar</span>
        </a>

    </div>

</nav>"""

update_file(explore_path, old_explore, new_explore)

# Itinerary List update
itinerary_path = 'src/main/resources/templates/traveler/itinerary/list.html'
old_itinerary = """<nav class="navbar">

    <div class="container">

        <div class="logo">
            ◉ TRIPINAJA
        </div>

        <div class="nav-center">
            <a href="/dashboard">Dashboard</a>
            <a href="/itinerary/list" class="active">
                My Trips
            </a>
        </div>

    </div>

</nav>"""

new_itinerary = """<nav class="navbar dashboard-navbar">

    <div class="container">

        <div class="logo">
            <span class="logo-icon">◉</span>
            TRIPINAJA
        </div>

        <div class="nav-center">
            <a href="/dashboard">Dashboard</a>
            <a href="/itinerary/list">My Trips</a>
        </div>

        <!-- PROFILE -->
        <a href="/profile" class="profile-menu">
            <div class="profile-avatar">A</div>
            <span class="profile-name">Akbar</span>
        </a>

    </div>

</nav>"""

update_file(itinerary_path, old_itinerary, new_itinerary)
